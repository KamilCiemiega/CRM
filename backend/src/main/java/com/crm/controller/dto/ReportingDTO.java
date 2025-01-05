package com.crm.controller.dto;

import com.crm.entity.Reporting;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String topic;
    @NotNull
    private Reporting.ReportingStatus status;
    @NotNull
    private Reporting.ReportingType type;
    private String description;
    @NotNull
    private ClientDTO client;
    @NotNull
    private UserDetailsDTO user;
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
    private List<MessageDTO> messages = new ArrayList<>();
    private List<TaskDetailsDTO> tasks = new ArrayList<>();
    private List<AttachmentDTO> attachments = new ArrayList<>();
}
