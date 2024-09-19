package com.crm.service.serviceImpl;


import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.dao.MessageFolderRepository;
import com.crm.dao.MessageRepository;
import com.crm.entity.Message;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageFolderRepository messageFolderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MessageFolderRepository messageFolderRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.messageFolderRepository = messageFolderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageDTO save(Message message) {
        messageRepository.save(message);
        return modelMapper.map(message, MessageDTO.class);
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

    @Override
    public MessageDTO CreateOrUpdateExistingMessage(MessageDTO messageDTO) {
        Message message = modelMapper.map(messageDTO, Message.class);

        if (message.getId() == null) {
            message.setSentDate(new Timestamp(System.currentTimeMillis()));
        }

        Message savedMessage = messageRepository.save(message);

        return modelMapper.map(savedMessage, MessageDTO.class);
    }

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
