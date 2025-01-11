package com.crm.service.serviceImpl;

import com.crm.dao.TaskRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Task;
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

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private <T> void processList(List<T> items, Consumer<T> processor) {
        if (items != null && !items.isEmpty()) {
            items.forEach(processor);
        }
    }

    private void processTask(Task task, Task parentTask) {
        if (taskRepository.existsByTopic(task.getTopic())) {
            throw new IllegalArgumentException("Topic must be unique. Value already exists: " + task.getTopic());
        }

        if (parentTask != null) {
            task.setParentTask(parentTask);
        }

        User userTaskWorker = findEntity(userRepository, task.getUserTaskWorker().getId(), "Working user on task");
        User userTaskCreator = findEntity(userRepository, task.getUserTaskCreator().getId(), "Created task user");
        task.setUserTaskWorker(userTaskWorker);
        task.setUserTaskCreator(userTaskCreator);

        processList(task.getUserNotifications(), notification -> {
            User user = findEntity(userRepository, notification.getUser().getId(), "User");
            notification.setUser(user);
            notification.setTaskNotification(task);
        });
        processList(task.getAttachments(), attachment -> attachment.setTask(task));
        processList(task.getSubTasks(), subTask -> processTask(subTask, task));
    }

    @Transactional
    @Override
    public Task save(Task task) {
        processTask(task, task.getParentTask());
        return taskRepository.save(task);
    }
}
