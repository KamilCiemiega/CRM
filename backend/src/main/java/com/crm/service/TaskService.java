package com.crm.service;

import com.crm.entity.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task);
    Task saveSubtask(Task subTask);
    List<Task> getAllTasks();
    Task updateTask(int taskId, Task task);
    Task deleteTask(int taskId);
}
