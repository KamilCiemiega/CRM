package com.crm.controller.dto;

import com.crm.entity.Task;

import java.util.List;

public class TaskDTO {
    private String topic;
    private String status;
    private String description;
    private Integer userCreatorId;
    private Integer userWorkerId;
    private Integer parentTaskId;
    private List<TaskDetailsDTO> subTasksDTOs;
}
