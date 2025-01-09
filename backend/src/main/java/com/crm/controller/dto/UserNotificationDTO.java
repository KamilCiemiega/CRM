package com.crm.controller.dto;

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
    private String type;
    private Integer ticketId;
    private Integer taskId;
    private Integer userId;
}
