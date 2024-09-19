package com.crm.service.serviceImpl;


import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.dao.MessageFolderRepository;
import com.crm.dao.MessageRepository;
import com.crm.entity.Message;
import com.crm.entity.MessageFolder;
import com.crm.entity.MessageParticipant;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public MessageDTO createNewMessage(MessageDTO messageDTO) {
        Message message = modelMapper.map(messageDTO, Message.class);
        messageRepository.save(message);


    }

    @Override
    public MessageDTO updateExistingMessage(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public MessageDTO createOrUpdateMessage(MessageDTO messageDTO) {
        Message updatedMessage;
        Message message = modelMapper.map(messageDTO, Message.class);

        if (message.getId() != null) {
            Optional<Message> existingMessage = messageRepository.findById(message.getId());

            if (existingMessage.isPresent()) {

                //Updating message
                updatedMessage = existingMessage.get();
                updatedMessage.setSubject(message.getSubject());
                updatedMessage.setBody(message.getBody());
                updatedMessage.setStatus(message.getStatus());
                updatedMessage.setSentDate(message.getSentDate());

                //Updating attachments
                if (message.getAttachments() != null) {
                    updatedMessage.getAttachments().clear();
                    updatedMessage.getAttachments().addAll(message.getAttachments());
                }

                List<MessageFolder> newFolders = message.getMessageFolders() != null ? message.getMessageFolders() : new ArrayList<>();
                List<MessageFolder> existingFolders = new ArrayList<>(updatedMessage.getMessageFolders());

                existingFolders.removeIf(folder -> !newFolders.contains(folder));
                for (MessageFolder folder : existingFolders) {
                    folder.getMessages().remove(updatedMessage);
                }

                for (MessageFolder folder : newFolders) {
                    if (!updatedMessage.getMessageFolders().contains(folder)) {
                        folder.getMessages().add(updatedMessage);
                    }
                }

                updatedMessage.setMessageFolders(newFolders);

                List<MessageParticipant> newParticipants = message.getMessageParticipants() != null ? message.getMessageParticipants() : new ArrayList<>();
                List<MessageParticipant> existingParticipants = new ArrayList<>(updatedMessage.getMessageParticipants());

                existingParticipants.removeIf(participant -> !newParticipants.contains(participant));
                for (MessageParticipant participant : existingParticipants) {
                    participant.getMessages().remove(updatedMessage);
                }

                for (MessageParticipant participant : newParticipants) {
                    if (!updatedMessage.getMessageParticipants().contains(participant)) {
                        participant.getMessages().add(updatedMessage);
                    }
                }

                updatedMessage.setMessageParticipants(newParticipants);

                messageRepository.save(updatedMessage);
            } else {
                throw new SendMessageExceptionHandlers.NoSuchMessageException("Message not found for ID: " + message.getId());
            }
        } else {
            updatedMessage = messageRepository.save(message);
        }
        return modelMapper.map(updatedMessage, MessageDTO.class);
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
