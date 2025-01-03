package com.crm.controller.dto;

import com.crm.entity.UserNotification;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Integer reportingId;
    private Integer taskId;
    private Integer userId;
}
