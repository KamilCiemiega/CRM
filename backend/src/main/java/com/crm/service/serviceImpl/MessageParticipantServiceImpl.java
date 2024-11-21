package com.crm.service;

import com.crm.controller.dto.MessageParticipantDTO;
import com.crm.dao.ClientRepository;
import com.crm.dao.MessageParticipantRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.MessageParticipant;
import com.crm.exception.NoSuchEntityException;
import org.springframework.stereotype.Service;

@Service
public class MessageParticipantServiceImpl implements MessageParticipantService{

    private final MessageParticipantRepository messageParticipantRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public MessageParticipantServiceImpl(MessageParticipantRepository messageParticipantRepository, UserRepository userRepository, ClientRepository clientRepository) {
        this.messageParticipantRepository = messageParticipantRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
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

    @Override
    public Object findUserOrClientByParticipantId(Integer participantId) {
        MessageParticipant messageParticipant = messageParticipantRepository.findById(participantId)
                .orElseThrow(() -> new NoSuchEntityException("Participant not found with ID: " + participantId));

        if (messageParticipant.getType() == MessageParticipant.ParticipantType.USER) {
            return userRepository.findById(messageParticipant.getUser().getId())
                    .orElseThrow(() -> new NoSuchEntityException("User not found with ID: " + messageParticipant.getUser().getId()));
        } else if (messageParticipant.getType() == MessageParticipant.ParticipantType.CLIENT) {
            return clientRepository.findById(messageParticipant.getClient().getId())
                    .orElseThrow(() -> new NoSuchEntityException("Client not found with ID: " + messageParticipant.getClient().getId()));
        }

        throw new IllegalArgumentException("Invalid participant type");
    }
}
