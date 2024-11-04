package com.crm.dao;

import com.crm.entity.MessageParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageParticipantRepository extends JpaRepository<MessageParticipant, Integer> {
    MessageParticipant findByUserId(Integer userId);
    MessageParticipant findByClientId(Integer clientId);
}
