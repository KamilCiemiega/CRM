package com.crm.controller.dto;
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
    private Integer userCreatorId;
    private Integer userWorkerId;
    private Integer parentTaskId;
    private List<TaskDetailsDTO> subTasksDTOs = new ArrayList<>();
    private List<UserNotificationDTO> userNotificationDTOs = new ArrayList<>();
    private Integer reportingId;
}
