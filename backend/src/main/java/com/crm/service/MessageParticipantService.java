package com.crm.service;
import com.crm.entity.MessageParticipant;

public interface MessageParticipantService {
    MessageParticipant findUserOrClientById(MessageParticipant.ParticipantType type, Integer userId, Integer clientId);
     Object findUserOrClientByParticipantId(Integer participantId);
}
