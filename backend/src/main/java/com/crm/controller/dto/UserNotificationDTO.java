package com.crm.controller.dto;

import com.crm.entity.UserNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDTO {
    private Integer id;
    private UserNotification.NotificationType type;
    private ReportingDetailsDTO reportingNotification;
    private TaskDetailsDTO taskNotification;
    private UserDetailsDTO user;
}
