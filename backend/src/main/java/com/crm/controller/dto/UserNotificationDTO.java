package com.crm.controller.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String type;
    private Integer ticketId;
    private Integer taskId;
    @NotNull
    private Integer userId;
}
