package com.crm.service.serviceImpl;


import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.dao.MessageRepository;
import com.crm.entity.Message;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        Message savedMessage = messageRepository.save(modelMapper.map(messageDTO, Message.class));
        return modelMapper.map(savedMessage, MessageDTO.class);
    }

    @Transactional
    @Override
    public MessageDTO updateMessage(int messageId, MessageDTO messageDTO) {
        Message existingMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchMessageException("Message not found for ID: " + messageId));

        existingMessage.setSubject(messageDTO.getSubject());
        existingMessage.setBody(messageDTO.getBody());
        existingMessage.setStatus(messageDTO.getStatus());
        existingMessage.setSentDate(messageDTO.getSentDate());

        Message updatedMessage = messageRepository.save(existingMessage);

        return modelMapper.map(updatedMessage, MessageDTO.class);
    }

    @Override
    public List<MessageDTO> findAllMessage() {
       return messageRepository.findAll()
                .stream()
                .map(m -> modelMapper.map(m, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageDTO> findById(int messageId) {
        return messageRepository.findById(messageId)
                .map(message -> modelMapper.map(message, MessageDTO.class));
    }

    @Transactional
    @Override
    public MessageDTO deleteMessage(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            Message deletedMessage = message.get();
            messageRepository.deleteById(messageId);
            return modelMapper.map(deletedMessage, MessageDTO.class);
        } else {
            throw new SendMessageExceptionHandlers.NoSuchMessageException("Can't find message with id " + messageId);
        }
    }

    @Override
    public List<MessageDTO> getSortedMessages(int folderId, MessageSortType sortType, String orderType) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(orderType) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = switch (sortType) {
            case SIZE -> Sort.by(direction, "size");
            case SUBJECT -> Sort.by(direction, "subject");
            default -> Sort.by(direction, "sentDate");
        };

        return messageRepository.findMessagesByFolderId(folderId, sort)
                .stream()
                .map(m -> modelMapper.map(m, MessageDTO.class))
                .collect(Collectors.toList());
    }
}
