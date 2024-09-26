package com.crm.service.serviceImpl;


import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.dao.MessageFolderRepository;
import com.crm.dao.MessageLocationRepository;
import com.crm.dao.MessageParticipantRepository;
import com.crm.dao.MessageRepository;
import com.crm.entity.*;
import com.crm.exception.NoSuchFolderException;
import com.crm.exception.NoSuchMessageException;
import com.crm.exception.NoSuchParticipantException;
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
    public MessageDTO save(MessageDTO messageDTO) {
        Message newMessage = modelMapper.map(messageDTO, Message.class);

        List<Attachment> attachments = messageDTO.getAttachments().stream()
                .map(attachmentDTO -> {
                    Attachment attachment = modelMapper.map(attachmentDTO, Attachment.class);
                    attachment.setMessage(newMessage);
                    return attachment;
                })
                .toList();

        newMessage.setAttachments(attachments);

        MessageFolder folder = messageFolderRepository.findById(messageDTO.getFolderId())
                .orElseThrow(() -> new NoSuchFolderException("Folder not found with id: " + messageDTO.getFolderId()));

        folder.getMessages().add(newMessage);
        newMessage.getMessageFolders().add(folder);

        List<MessageRole> messageRoles = messageDTO.getMessageRoles().stream()
                .map(roleDTO -> {
                    MessageRole messageRole = new MessageRole();
                    messageRole.setMessage(newMessage);
                    messageRole.setStatus(roleDTO.getStatus());

                    MessageParticipant participant = messageParticipantRepository.findById(roleDTO.getParticipantId())
                            .orElseThrow(() -> new NoSuchParticipantException("Participant not found with id: " + roleDTO.getParticipantId()));
                    messageRole.setParticipant(participant);

                    return messageRole;
                })
                .toList();

        newMessage.setMessageRoles(messageRoles);

        messageFolderRepository.save(folder);
        Message savedMessage = messageRepository.save(newMessage);

        return modelMapper.map(savedMessage, MessageDTO.class);
    }



    @Transactional
    @Override
    public MessageDTO updateMessage(int messageId, MessageDTO messageDTO) {
        Message existingMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchMessageException("Message not found for ID: " + messageId));

        existingMessage.setSubject(messageDTO.getSubject());
        existingMessage.setBody(messageDTO.getBody());
        existingMessage.setStatus(messageDTO.getStatus());
        existingMessage.setSentDate(messageDTO.getSentDate());


        MessageFolder newFolder = messageFolderRepository.findById(messageDTO.getFolderId())
                .orElseThrow(() -> new NoSuchMessageException("Folder not found for ID: " + messageDTO.getFolderId()));

        List<MessageLocation> existingLocations = messageLocationRepository.findByMessageId(messageId);
        existingLocations.forEach(messageLocationRepository::delete);

        MessageLocation newLocation = new MessageLocation();
        newLocation.setFolderId(newFolder.getId());
        newLocation.setMessageId(existingMessage.getId());
        messageLocationRepository.save(newLocation);

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
        messageLocationRepository.deleteByMessageId(messageId);
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            Message deletedMessage = message.get();

            messageRepository.delete(deletedMessage);
            return modelMapper.map(deletedMessage, MessageDTO.class);
        } else {
            throw new NoSuchMessageException("Can't find message with id " + messageId);
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
