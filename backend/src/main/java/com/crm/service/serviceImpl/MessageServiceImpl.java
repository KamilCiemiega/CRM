package com.crm.service.serviceImpl;


import com.crm.dao.MessageRepository;
import com.crm.entity.Message;
import com.crm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAllMessage() { return messageRepository.findAll(); }

    @Override
    public Optional<Message> findById(int messageId) {return messageRepository.findById(messageId); }
}
