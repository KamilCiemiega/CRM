package com.crm.service.serviceImpl;


import com.crm.enums.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.dao.MessageFolderRepository;
import com.crm.dao.MessageLocationRepository;
import com.crm.dao.MessageParticipantRepository;
import com.crm.dao.MessageRepository;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final MessageFolderRepository messageFolderRepository;
    private final MessageLocationRepository messageLocationRepository;
    private final MessageParticipantRepository messageParticipantRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper, MessageFolderRepository messageFolderRepository, MessageLocationRepository messageLocationRepository, MessageParticipantRepository messageParticipantRepository) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.messageFolderRepository = messageFolderRepository;
        this.messageLocationRepository = messageLocationRepository;
        this.messageParticipantRepository = messageParticipantRepository;
    }

    @Transactional
    @Override
    public Message save(Message message) {
        List<Attachment> attachments = message.getAttachments().stream()
                .peek(attachment -> attachment.setMessage(message))
                .toList();

        message.setAttachments(attachments);

        MessageFolder folder = message.getMessageFolders().stream()
                .findFirst()
                .flatMap(folderId -> messageFolderRepository.findById(folderId.getId()))
                .orElseThrow(() -> new NoSuchEntityException("Folder not found"));

        folder.getMessages().add(message);
        message.getMessageFolders().add(folder);

        List<MessageRole> messageRoles = message.getMessageRoles().stream()
                .peek(role -> {
                    role.setMessage(message);
                    role.setStatus(role.getStatus());

                    MessageParticipant participant = message.getMessageRoles().stream()
                            .findFirst()
                            .flatMap(messageRoleId -> messageParticipantRepository.findById(messageRoleId.getId()))
                            .orElseThrow(() -> new NoSuchEntityException("Participant not found"));
                    role.setParticipant(participant);

                })
                .toList();

        message.setMessageRoles(messageRoles);

        messageFolderRepository.save(folder);
        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public Message updateMessage(int messageId, Message message) {
        Message existingMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchEntityException("Message not found for ID: " + messageId));

        existingMessage.setSubject(message.getSubject());
        existingMessage.setBody(message.getBody());
        existingMessage.setStatus(message.getStatus());
        existingMessage.setSentDate(message.getSentDate());

        MessageFolder newFolder = message.getMessageFolders().stream()
                .findFirst()
                .flatMap(folderId -> messageFolderRepository.findById(folderId.getId()))
                .orElseThrow(() -> new NoSuchEntityException("Folder not found" ));

        List<MessageLocation> existingLocations = messageLocationRepository.findByMessageId(messageId);
                existingLocations.forEach(messageLocationRepository::delete);

        MessageLocation newLocation = new MessageLocation();
        newLocation.setFolderId(newFolder.getId());
        newLocation.setMessageId(existingMessage.getId());
        messageLocationRepository.save(newLocation);

        return messageRepository.save(existingMessage);
    }

    @Override
    public List<Message> findAllMessage() {
       return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(int messageId) {
       return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchEntityException("Can't find message with id " + messageId));
    }

    @Transactional
    @Override
    public Message deleteMessage(int messageId) {
        messageLocationRepository.deleteByMessageId(messageId);
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            Message deletedMessage = message.get();
            messageRepository.delete(deletedMessage);

            return deletedMessage;
        } else {
            throw new NoSuchEntityException("Can't find message with id " + messageId);
        }
    }

    @Override
    public List<Message> getSortedMessages(int folderId, MessageSortType sortType, String orderType) {
        Sort.Direction direction = "DESC".equalsIgnoreCase(orderType) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = switch (sortType) {
            case SIZE -> Sort.by(direction, "size");
            case SUBJECT -> Sort.by(direction, "subject");
            default -> Sort.by(direction, "sentDate");
        };

        return messageRepository.findMessagesByFolderId(folderId, sort);
    }
}
