package com.crm.controller.dto;

import com.crm.entity.Message;
import com.crm.entity.Reporting;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportingDTO {
    private String topic;
    @Enumerated(EnumType.STRING)
    private Reporting.ReportingStatus reportingStatus;
    private String description;
    private Integer companyId;
    private Integer assignedUserId;
    private List<UserNotificationDTO> userNotificationDTOs = new ArrayList<>();
    private List<MessageDTO> messageDTOs = new ArrayList<>();
    private List<TaskDTO> taskDTOs = new ArrayList<>();
}
