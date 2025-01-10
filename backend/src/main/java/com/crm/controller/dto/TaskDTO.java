package com.crm.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private UserDTO userTaskCreator;
    @NotNull
    private UserDTO userTaskWorker;
    private TaskDTO parentTask;
    private Integer ticketId;
    private List<TaskDTO> subTasks = new ArrayList<>();
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
    private List<AttachmentDTO> attachments = new ArrayList<>();
}
