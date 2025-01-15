package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.EntityFinder;
import com.crm.service.TaskService;
import com.crm.utils.EntityProcessorHelper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class TaskServiceImpl implements TaskService, EntityFinder {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final AttachmentRepository attachmentRepository;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, TicketRepository ticketRepository, UserNotificationRepository userNotificationRepository, AttachmentRepository attachmentRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public List<Task> getAllTasks() {
       return taskRepository.findAll();
    }

    @Transactional
    @Override
    public Task saveTask(Task task) {
        Ticket ticket = findEntity(ticketRepository, task.getTicket().getId(), "Ticket");
        return processAndSaveTask(task, null, ticket);
    }

    @Transactional
    @Override
    public Task saveSubtask(Task subTask) {
        Task parentTask = findEntity(taskRepository, subTask.getParentTask().getId(), "Parent Task");
        return processAndSaveTask(subTask, parentTask, null);
    }

    @Transactional
    @Override
    public Task updateTask(int taskId, Task updatedTask) {
        Task exisitingTask = findEntity(taskRepository, taskId, "Task");

        if (!exisitingTask.getTopic().equals(updatedTask.getTopic())) {
            exisitingTask.setTopic(updatedTask.getTopic());
        }
        exisitingTask.setStatus(updatedTask.getStatus());
        exisitingTask.setDescription(updatedTask.getDescription());

        if (!exisitingTask.getUserTaskCreator().getId().equals(updatedTask.getUserTaskCreator().getId())){
            User userTaskCreator = findEntity(userRepository, updatedTask.getUserTaskCreator().getId(), "User task creator");
            exisitingTask.setUserTaskCreator(userTaskCreator);
        }
        if (!exisitingTask.getAssignedUserTask().getId().equals(updatedTask.getAssignedUserTask().getId())){
            User assignedUser = findEntity(userRepository, updatedTask.getAssignedUserTask().getId(), "Assigned user");
            exisitingTask.setAssignedUserTask(assignedUser);
        }
        Optional.ofNullable(updatedTask.getTicket())
                .map(Ticket::getId)
                .ifPresent(ticketId -> exisitingTask.setTicket(findEntity(ticketRepository, ticketId, "Ticket")));

        exisitingTask.getUserNotifications().clear();
        exisitingTask.getUserNotifications().addAll(processUserNotifications(updatedTask.getUserNotifications(), exisitingTask));

        exisitingTask.getAttachments().clear();
        exisitingTask.getAttachments().addAll(processAttachments(updatedTask.getAttachments(), exisitingTask));

        return taskRepository.save(exisitingTask);
    }

    @Transactional
    @Override
    public Task deleteTask(int taskId) {
        Task foundetTask = findEntity(taskRepository, taskId, "Task");
        taskRepository.delete(foundetTask);
        return foundetTask;
    }

    private <T> void processList(List<T> items, Consumer<T> processor) {
        if (items != null && !items.isEmpty()) {
            items.forEach(processor);
        }
    }

    private Task processAndSaveTask(Task task, Task parentTask, Ticket ticket) {
        if (taskRepository.existsByTopic(task.getTopic())) {
            throw new IllegalArgumentException("Topic must be unique. Value already exists: " + task.getTopic());
        }

        User taskCreator = findEntity(userRepository, task.getUserTaskCreator().getId(), "User who created task");
        User taskAssigned = findEntity(userRepository, task.getAssignedUserTask().getId(), "Assigned user");
        task.setUserTaskCreator(taskCreator);
        task.setAssignedUserTask(taskAssigned);
        taskCreator.getCreatedTasks().add(task);
        taskAssigned.getAssignedTasks().add(task);

        if (ticket != null){
            task.setTicket(ticket);
            ticket.getTasks().add(task);
        }
       if (parentTask != null){
           task.setParentTask(parentTask);
           parentTask.getSubTasks().add(task);
       }
        processList(task.getUserNotifications(), notification -> {
            User user = findEntity(userRepository, notification.getUser().getId(), "User");
            notification.setUser(user);
            notification.setTaskNotification(task);
        });

        processList(task.getAttachments(), attachment -> attachment.setTask(task));

        return taskRepository.save(task);
    }

    private List<UserNotification> processUserNotifications(List<UserNotification> notifications, Task task) {
        return new EntityProcessorHelper().processUserNotifications(
                notifications,
                task,
                userNotificationRepository,
                userRepository,
                UserNotification::setTaskNotification
        );
    }

    private List<Attachment> processAttachments(List<Attachment> attachments, Task task) {
        return  new EntityProcessorHelper().processAttachments(
                attachments,
                task,
                attachmentRepository,
                Attachment::setTask
        );
    }
}
