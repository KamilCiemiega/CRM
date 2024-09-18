package com.crm.service;

import com.crm.controller.dto.MessageParticipantDTO;
import java.util.List;
import java.util.Optional;

public interface MessageParticipantService {
    MessageParticipantDTO save(MessageParticipantDTO participantDTO);
    List<MessageParticipantDTO> findAllParticipants();
    Optional<MessageParticipantDTO> findById(int participantId);
    void deleteParticipant(int participantId);
}
