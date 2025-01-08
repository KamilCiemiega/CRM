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
<<<<<<< HEAD
    private UserNotification.NotificationType type;
    private ReportingDetailsDTO reportingNotification;
    private TaskDetailsDTO taskNotification;
    private UserDetailsDTO user;
}
=======
    private String type;
    private Integer ticketId;
    private Integer taskId;
    private Integer userId;
}
>>>>>>> 9421af8 (mostly working on TicketService save method)
