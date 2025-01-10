package com.crm.controller.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleDTO roleDTO;
    private TaskDTO workingTask;
    private TaskDTO createdTask;
    private List<TicketDTO> tickets = new ArrayList<>();
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
}