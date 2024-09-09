package com.crm.service;
import com.crm.entity.Message;
import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> findAllMessage();
    Optional<Message> findById(int messageId);

}
