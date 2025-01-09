package com.crm.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
    private Integer id;
    @NotNull
    private String topic;
    @NotNull
    private String status;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
    @NotNull
    private Integer taskCreatorId;
    @NotNull
    private Integer taskWorkerId;
    private TaskDTO parentTask;
    private Integer ticketId;
    private List<TaskDTO> subTasks = new ArrayList<>();
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
    private List<AttachmentDTO> attachments = new ArrayList<>();
}
