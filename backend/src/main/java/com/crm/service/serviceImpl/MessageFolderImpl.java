package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageFolderDto;
import com.crm.dao.MessageFolderRepository;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.exception.NoSuchFolderException;
import com.crm.exception.NoSuchUserException;
import com.crm.service.MessageFolderService;
import com.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageFolderImpl implements MessageFolderService {

    private final MessageFolderRepository messageFolderRepository;
    private final UserService userService;
    private final MessageFolderService messageFolderService;

    private final ModelMapper modelMapper;

    @Autowired
    public MessageFolderImpl(MessageFolderRepository messageFolderRepository, UserService userService, MessageFolderService messageFolderService, ModelMapper modelMapper) {
        this.messageFolderRepository = messageFolderRepository;
        this.userService = userService;
        this.messageFolderService = messageFolderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageFolder save(MessageFolder messageFolder) {
         return messageFolderRepository.save(messageFolder);
    }

    @Override
    public List<MessageFolder> findAllMessageFolders() {
        return messageFolderRepository.findAll();
    }

    @Override
    public Optional<MessageFolder> findByNameAndUser(String messageFolderName, User user) {
        return messageFolderRepository.findByNameAndUser(messageFolderName, user);
    }

    @Override
    public Optional<MessageFolder> findById(int parentFolderId) {
        return messageFolderRepository.findById(parentFolderId);
    }

    @Override
    public void deleteFolder(int folderId) {
        messageFolderRepository.deleteById(folderId);
    }

    @Override
    public MessageFolderDto createOrUpdateMessageFolder(MessageFolderDto messageFolderDto) {
        Optional<User> user = userService.findById(messageFolderDto.getOwnerUserId());
        if (user.isEmpty()) {
            throw new NoSuchUserException("User not found for ID: " + messageFolderDto.getOwnerUserId());
        }

        MessageFolder parentFolder = null;
        if (messageFolderDto.getParentFolderId() != null) {
            Optional<MessageFolder> optionalParentFolder = messageFolderService.findById(messageFolderDto.getParentFolderId());
            if (optionalParentFolder.isPresent()) {
                parentFolder = optionalParentFolder.get();
            } else {
                throw new NoSuchFolderException("Parent folder not found for ID: " + messageFolderDto.getParentFolderId());
            }
        }

        MessageFolder messageFolder;
        if (messageFolderDto.getId() != null) {
            Optional<MessageFolder> existingFolder = messageFolderService.findById(messageFolderDto.getId());
            if (existingFolder.isPresent()) {
                messageFolder = existingFolder.get();
                messageFolder.setName(messageFolderDto.getName());
                messageFolder.setParentFolder(parentFolder);
                messageFolder.setUser(user.get());
            } else {
                throw new NoSuchFolderException("Folder not found for ID: " + messageFolderDto.getId());
            }
        } else {
            messageFolder = new MessageFolder();
            messageFolder.setName(messageFolderDto.getName());
            messageFolder.setParentFolder(parentFolder);
            messageFolder.setUser(user.get());
        }

        messageFolder = messageFolderService.save(messageFolder);

        return modelMapper.map(messageFolder, MessageFolderDto.class);
    }

}
