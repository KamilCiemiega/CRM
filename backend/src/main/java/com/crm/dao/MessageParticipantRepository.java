package com.crm.dao;

import com.crm.entity.MessageParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageParticipantRepository extends JpaRepository<MessageParticipant, Integer> {}
