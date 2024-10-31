package com.crm.service.serviceImpl;

import com.crm.enums.MessageSortType;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageFolderRepository messageFolderRepository;
    private final MessageLocationRepository messageLocationRepository;
    private final MessageParticipantRepository messageParticipantRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper, MessageFolderRepository messageFolderRepository, MessageLocationRepository messageLocationRepository, MessageParticipantRepository messageParticipantRepository) {
        this.messageRepository = messageRepository;
        this.messageFolderRepository = messageFolderRepository;
        this.messageLocationRepository = messageLocationRepository;
        this.messageParticipantRepository = messageParticipantRepository;
    }

    @Transactional
    @Override
    public Message save(Message message) {
        if (!message.getAttachments().isEmpty()) {
            List<Attachment> attachments = message.getAttachments().stream()
                    .peek(attachment -> attachment.setMessage(message))
                    .toList();
            message.setAttachments(attachments);
        }

        List<MessageFolder> messageFolders = message.getMessageFolders().stream()
                .map(folder -> {
                    return messageFolderRepository.findById(folder.getId())
                            .orElseThrow(() -> new NoSuchEntityException("Folder not found"));
                })
                .toList();
        message.setMessageFolders(messageFolders);

        List<MessageRole> messageRoles = message.getMessageRoles().stream()
                .peek(role -> {
                    role.setMessage(message);
                    role.setStatus(role.getStatus());

                    MessageParticipant participant = messageParticipantRepository.findById(role.getParticipant().getId())
                            .orElseThrow(() -> new NoSuchEntityException("Participant not found"));
                    role.setParticipant(participant);
                })
                .toList();

        message.setMessageRoles(messageRoles);

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

        message.getMessageFolders().stream()
                .map(folder -> {
                   //sprawdzic czy foldery dla tej wiadomosci sie roznia
                   // jesli tak to wtedy wykonac update
                   messageFolderRepository.findById(folder.getId())
                            .orElseThrow(() -> new NoSuchEntityException("Folder not found" ));


                })


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
