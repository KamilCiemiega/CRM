package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageParticipantDTO;
import com.crm.dao.MessageParticipantRepository;
import com.crm.entity.MessageParticipant;
import com.crm.service.MessageParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageParticipantServiceImpl implements MessageParticipantService {

    private final MessageParticipantRepository participantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageParticipantServiceImpl(MessageParticipantRepository participantRepository, ModelMapper modelMapper) {
        this.participantRepository = participantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageParticipantDTO save(MessageParticipantDTO participantDTO) {
        MessageParticipant participant = modelMapper.map(participantDTO, MessageParticipant.class);
        MessageParticipant savedParticipant = participantRepository.save(participant);
        return modelMapper.map(savedParticipant, MessageParticipantDTO.class);
    }

    @Override
    public List<MessageParticipantDTO> findAllParticipants() {
        return participantRepository.findAll()
                .stream()
                .map(participant -> modelMapper.map(participant, MessageParticipantDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageParticipantDTO> findById(int participantId) {
        return participantRepository.findById(participantId)
                .map(participant -> modelMapper.map(participant, MessageParticipantDTO.class));
    }

    @Override
    public void deleteParticipant(int participantId) {
        participantRepository.deleteById(participantId);
    }
}
