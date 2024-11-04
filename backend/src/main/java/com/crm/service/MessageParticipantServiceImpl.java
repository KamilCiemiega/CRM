package com.crm.service;

import com.crm.controller.dto.MessageParticipantDTO;
import com.crm.dao.MessageParticipantRepository;
import com.crm.entity.MessageParticipant;
import com.crm.exception.NoSuchEntityException;
import org.springframework.stereotype.Service;

@Service
public class MessageParticipantServiceImpl implements MessageParticipantService{

    private final MessageParticipantRepository messageParticipantRepository;

    public MessageParticipantServiceImpl(MessageParticipantRepository messageParticipantRepository) {
        this.messageParticipantRepository = messageParticipantRepository;
    }

    @Override
    public MessageParticipant findUserOrClientById(MessageParticipant.ParticipantType type, Integer userId, Integer clientId) {

        if (type == MessageParticipant.ParticipantType.USER && userId != null) {
            return messageParticipantRepository.findByUserId(userId);
        } else if (type == MessageParticipant.ParticipantType.CLIENT && clientId != null) {
            return messageParticipantRepository.findByClientId(clientId);
        }

        throw new NoSuchEntityException("Participant not found with given criteria: " + type + clientId);
    }
}
