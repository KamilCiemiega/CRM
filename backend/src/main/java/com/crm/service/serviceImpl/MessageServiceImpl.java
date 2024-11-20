package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.enums.MessageSortType;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageFolderRepository messageFolderRepository;
    private final MessageLocationRepository messageLocationRepository;
    private final MessageParticipantRepository messageParticipantRepository;
    private final AttachmentRepository attachmentRepository;
    private final MessageRoleRepository messageRoleRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper, MessageFolderRepository messageFolderRepository, MessageLocationRepository messageLocationRepository, MessageParticipantRepository messageParticipantRepository, AttachmentRepository attachmentRepository, MessageRoleRepository messageRoleRepository) {
        this.messageRepository = messageRepository;
        this.messageFolderRepository = messageFolderRepository;
        this.messageLocationRepository = messageLocationRepository;
        this.messageParticipantRepository = messageParticipantRepository;
        this.attachmentRepository = attachmentRepository;
        this.messageRoleRepository = messageRoleRepository;
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

        existingMessage.getAttachments().clear();
        message.getAttachments().forEach(attachment -> {
            if (attachment.getId() != null) {
                Attachment managedAttachment = attachmentRepository.findById(attachment.getId())
                        .orElseThrow(() -> new NoSuchEntityException("Attachment not found for ID: " + attachment.getId()));
                managedAttachment.setFilePath(attachment.getFilePath());
                existingMessage.getAttachments().add(managedAttachment);
            } else {
                attachment.setMessage(existingMessage);
                existingMessage.getAttachments().add(attachment);
            }
        });

        List<MessageFolder> messageFolders = message.getMessageFolders().stream()
                .map(folder -> {
                   return messageFolderRepository.findById(folder.getId())
                            .orElseThrow(() -> new NoSuchEntityException("Folder not found" ));
                })
                .collect(Collectors.toCollection(ArrayList::new));

        existingMessage.setMessageFolders(messageFolders);

        existingMessage.getMessageRoles().clear();
        message.getMessageRoles().forEach(role -> {
            if (role.getId() != null) {
                MessageRole managedRole = messageRoleRepository.findById(role.getId())
                        .orElseThrow(() -> new NoSuchEntityException("MessageRole not found for ID: " + role.getId()));

                managedRole.setStatus(role.getStatus());

                MessageParticipant participant = messageParticipantRepository.findById(role.getParticipant().getId())
                        .orElseThrow(() -> new NoSuchEntityException("Participant not found for ID: " + role.getParticipant().getId()));
                managedRole.setParticipant(participant);

                managedRole.setMessage(existingMessage);
                existingMessage.getMessageRoles().add(managedRole);
            } else {
                MessageParticipant participant = messageParticipantRepository.findById(role.getParticipant().getId())
                        .orElseThrow(() -> new NoSuchEntityException("Participant not found for ID: " + role.getParticipant().getId()));
                role.setParticipant(participant);
                role.setMessage(existingMessage);
                existingMessage.getMessageRoles().add(role);
            }
        });


        List<MessageLocation> existingLocations = messageLocationRepository.findByMessageId(messageId);
                existingLocations.forEach(messageLocationRepository::delete);

        messageFolders.forEach(folder -> {
            MessageLocation newLocation = new MessageLocation();
            newLocation.setFolderId(folder.getId());
            newLocation.setMessageId(existingMessage.getId());
            messageLocationRepository.save(newLocation);
        });

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
