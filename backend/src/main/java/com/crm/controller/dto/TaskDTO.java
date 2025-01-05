package com.crm.controller.dto;
import com.crm.entity.Attachment;
import com.crm.entity.Task;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String topic;
    private Task.TaskStatus status;
    private String description;
    private UserDetailsDTO userTaskCreator;
    private UserDetailsDTO userTaskWorker;
    private TaskDetailsDTO parentTask;
    private List<TaskDetailsDTO> subTasks = new ArrayList<>();
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
    private ReportingDetailsDTO reporting;
    private List<AttachmentDTO> attachments = new ArrayList<>();
}
