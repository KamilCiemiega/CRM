package com.crm.service.serviceImpl;

import com.crm.dao.TaskRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Task;
import com.crm.entity.Ticket;
import com.crm.entity.User;
import com.crm.service.EntityFinder;
import com.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
public class TaskServiceImpl implements TaskService, EntityFinder {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
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

        if (ticket != null) {
            task.setTicket(ticket);
            ticket.getTasks().add(task);
        }

        if (parentTask != null) {
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
    public Task updateTask(int taskId, Task task) {
        return null;
    }

}
