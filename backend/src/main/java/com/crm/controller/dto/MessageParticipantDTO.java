package com.crm.controller.dto;

import com.crm.entity.MessageParticipant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageParticipantDTO {
    private Integer id;
    private MessageParticipant.ParticipantType type;
    private ClientDTO client;
    private UserDetailsDTO user;
    private List<MessageRoleDTO> messageRole;
}
