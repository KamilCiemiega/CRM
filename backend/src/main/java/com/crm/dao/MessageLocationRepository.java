package com.crm.dao;

import com.crm.entity.MessageLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageLocationRepository extends JpaRepository<MessageLocation, Integer> {
    void deleteByMessageId(Integer messageId);
    List<MessageLocation> findByMessageId(int messageId);
}
