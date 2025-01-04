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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportingDTO {
    private String topic;
    private Reporting.ReportingStatus reportingStatus;
    private String description;
    private CompanyDTO company;
    private UserDetailsDTO user;
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
    private List<MessageDTO> messages = new ArrayList<>();
    private List<TaskDetailsDTO> tasks = new ArrayList<>();
}
