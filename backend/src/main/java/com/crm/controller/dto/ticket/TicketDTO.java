package com.crm.controller.dto.ticket;

import com.crm.controller.dto.AttachmentDTO;
import com.crm.controller.dto.UserNotificationDTO;
import com.crm.controller.dto.message.SimpleMessageDTO;
import com.crm.controller.dto.task.SimpleTaskDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Integer id;
    @NotNull
    private String topic;
    @NotNull
    private String status;
    @NotNull
    private String type;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
    @NotNull
    private Integer clientId;
    @NotNull
    private Integer userId;
    private List<AttachmentDTO> attachments = new ArrayList<>();
    private List<SimpleMessageDTO> messages = new ArrayList<>();
    private List<UserNotificationDTO> userNotifications = new ArrayList<>();
    private List<SimpleTaskDTO> tasks = new ArrayList<>();
}
