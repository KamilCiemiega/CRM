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
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskServiceImpl.class);

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

    @Override
    public List<Task> getAllTasks() {
     return taskRepository.findAll();
    }

    @Transactional
    @Override
    public Task saveTask(Task task) {
        if (taskRepository.existsByTopic(task.getTopic())) {
            throw new IllegalArgumentException("Topic must be unique. Value already exists: " + task.getTopic());
        }

        task.getUserTaskCreator().getCreatedTasks().add(task);
        task.getAssignedUserTask().getAssignedTasks().add(task);

        processList(task.getUserNotifications(), notification -> {
            User user = findEntity(userRepository, notification.getUser().getId(), "User");
            notification.setUser(user);
            notification.setTaskNotification(task);
        });
        processList(task.getAttachments(), attachment -> attachment.setTask(task));

        return taskRepository.save(task);
    }

    @Override
    public Task saveSubtask(Task subTask) {
        return null;
    }

}
