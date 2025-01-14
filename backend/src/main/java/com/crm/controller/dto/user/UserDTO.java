package com.crm.controller.dto.user;

import com.crm.controller.dto.RoleDTO;
import com.crm.controller.dto.task.TaskDTO;
import com.crm.controller.dto.ticket.TicketDTO;
import com.crm.controller.dto.UserNotificationDTO;
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
    private List<TaskDTO> assignedTasks = new ArrayList<>();
    private List<TaskDTO> createdTask = new ArrayList<>();
    private List<TicketDTO> tickets = new ArrayList<>();
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
}