package com.crm.controller.dto;


import com.crm.entity.MessageRole;
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
public class MessageRoleDTO {
    private Integer id;
    private MessageRole.RoleStatus status;
    private Integer participantId;
    private String email;
    private MessageParticipantDTO messageParticipant;
}
