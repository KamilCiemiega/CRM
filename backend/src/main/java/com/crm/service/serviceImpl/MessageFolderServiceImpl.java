package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDTO;
import com.crm.dao.MessageFolderRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Message;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.exception.DeleteDefaultFolderException;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.MessageFolderService;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageFolderServiceImpl implements MessageFolderService {

    private final MessageFolderRepository messageFolderRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageFolderServiceImpl(MessageFolderRepository messageFolderRepository,MessageService messageService,ModelMapper modelMapper, UserRepository userRepository) {
        this.messageFolderRepository = messageFolderRepository;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<MessageFolder> findAllMessageFolders() {
        return messageFolderRepository.findAll();
    }


    @Override
    @Transactional
    public MessageFolder save(MessageFolder messageFolder) {
        if (messageFolder.getName() == null || messageFolder.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Folder name cannot be null or empty.");
        }

        if (messageFolder.getParentFolder() != null && messageFolder.getParentFolder().getId() != null) {
            MessageFolder parentFolder = messageFolderRepository.findById(messageFolder.getParentFolder().getId())
                    .orElseThrow(() -> new NoSuchEntityException("Parent folder not found for ID: " + messageFolder.getParentFolder().getId()));
            messageFolder.setParentFolder(parentFolder);
        } else {
            messageFolder.setParentFolder(null);
        }

        if (messageFolder.getUser() != null && messageFolder.getUser().getId() != null) {
            User user = userRepository.findById(messageFolder.getUser().getId())
                    .orElseThrow(() -> new NoSuchEntityException("User not found for ID: " + messageFolder.getUser().getId()));
            messageFolder.setUser(user);
        } else {
            throw new IllegalArgumentException("Owner user ID must be provided.");
        }

        return messageFolderRepository.save(messageFolder);
    }


    @Override
    @Transactional
    public MessageFolder updateMessageFolder(int folderId, MessageFolder messageFolder) {
        MessageFolder existingFolder = messageFolderRepository.findById(folderId)
                .orElseThrow(() -> new NoSuchEntityException("Folder not found for ID: " + folderId));

        existingFolder.setName(messageFolder.getName());

        if (messageFolder.getParentFolder().getId() != null) {
            MessageFolder parentFolder = messageFolderRepository.findById(messageFolder.getParentFolder().getId())
                    .orElseThrow(() -> new NoSuchEntityException("Parent folder not found for ID: " + messageFolder.getParentFolder().getId()));
            existingFolder.setParentFolder(parentFolder);
        } else {
            existingFolder.setParentFolder(null);
        }

        if (messageFolder.getUser().getId() != null) {
            User user = userRepository.findById(messageFolder.getUser().getId())
                    .orElseThrow(() -> new NoSuchEntityException("User not found for ID: " + messageFolder.getUser().getId()));
            existingFolder.setUser(user);
        }
        existingFolder.setFolderType(messageFolder.getFolderType());

        return messageFolderRepository.save(existingFolder);
    }

    @Override
    @Transactional
    public MessageFolder deleteFolder(int folderId) {
        return messageFolderRepository.findById(folderId)
                .filter(folder -> !folder.getFolderType().equals("system"))
                .map(folder -> {
                    messageFolderRepository.deleteById(folderId);
                    return folder;
                })
                .orElseThrow(() -> new DeleteDefaultFolderException(
                        "Cannot delete the default folder or folder not found for ID: " + folderId
                ));
    }

    @Override
    @Transactional
    public List<Message> deleteAllMessagesFromFolder(int folderId) {
        Optional<MessageFolder> folder = messageFolderRepository.findById(folderId);

        return folder.map(presentFolder -> {
            List<Message> listOfDeletedMessages = new ArrayList<>();

            presentFolder.getMessages().forEach(message -> {
                Message deletedMessage = messageService.deleteMessage(message.getId());
                listOfDeletedMessages.add(deletedMessage);
            });
            return listOfDeletedMessages;
        }).orElseThrow(() -> new NoSuchEntityException("Folder doesn't exist " + folderId));
    }
}
