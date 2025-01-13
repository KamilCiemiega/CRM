package com.crm.controller;

import com.crm.controller.dto.task.TaskDTO;
import com.crm.dao.UserRepository;
import com.crm.entity.Task;
import com.crm.service.EntityFinder;
import com.crm.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/tasks")
public class TaskController  implements EntityFinder {
    private final ModelMapper modelMapper;
    private final TaskService taskService;
    private final UserRepository userRepository;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskController.class);

    public TaskController(ModelMapper modelMapper, TaskService taskService, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        List<Task> listOfTasks = taskService.getAllTasks();
        List<TaskDTO> listOfTasksDTOs = listOfTasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .toList();

        return ok(listOfTasksDTOs);
    }

    @PostMapping()
    public ResponseEntity<TaskDTO> saveNewTask(@RequestBody TaskDTO taskDTO) {
        Task mappedTask = modelMapper.map(taskDTO, Task.class);
        Task savedTask = taskService.saveTask(mappedTask);

        return ok(modelMapper.map(savedTask, TaskDTO.class));
    }

    @PostMapping("/subTask")
    public ResponseEntity<TaskDTO> saveSubTask(@RequestBody TaskDTO taskDTO) {
        Task mappedSubTask = modelMapper.map(taskDTO, Task.class);
        logger.debug("task", mappedSubTask);
        Task savedSubTask = taskService.saveSubtask(mappedSubTask);

        return ok(modelMapper.map(savedSubTask, TaskDTO.class));
    }

    @PostMapping("/{task-id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("task-id") int taskId, @RequestBody TaskDTO taskDTO){
        Task updatedTask = taskService.updateTask(taskId, modelMapper.map(taskDTO, Task.class));

        return ok(modelMapper.map(updatedTask, TaskDTO.class));
    }
}
