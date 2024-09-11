package com.crm.service.serviceImpl;


import com.crm.dao.MessageRepository;
import com.crm.entity.Message;
import com.crm.entity.MessageFolder;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findAllMessage() { return messageRepository.findAll(); }

    @Override
    public Optional<Message> findById(int messageId) { return messageRepository.findById(messageId); }

    @Override
    public Message createOrUpdateMessage(Message message) {
        Message updatedMessage;

        if (message.getId() != null) {
            Optional<Message> existingMessage = messageRepository.findById(message.getId());
            if (existingMessage.isPresent()) {
                updatedMessage = existingMessage.get();
                updatedMessage.setSubject(message.getSubject());
                updatedMessage.setBody(message.getBody());
                updatedMessage.setStatus(message.getStatus());
                updatedMessage.setSentDate(message.getSentDate());

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

                return messageRepository.save(updatedMessage);
            } else {
                throw new SendMessageExceptionHandlers.NoSuchMessageException("Message not found for ID: " + message.getId());
            }
        } else {
            return messageRepository.save(message);
        }
    }
}
