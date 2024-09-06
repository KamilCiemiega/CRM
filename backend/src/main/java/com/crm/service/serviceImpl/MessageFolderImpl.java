package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageFolderDto;
import com.crm.dao.MessageFolderRepository;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.exception.DuplicateFolderException;
import com.crm.exception.NoSuchFolderException;
import com.crm.exception.NoSuchUserException;
import com.crm.service.MessageFolderService;
import com.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageFolderImpl implements MessageFolderService {

    private final MessageFolderRepository messageFolderRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(MessageFolderImpl.class);

    @Autowired
    public MessageFolderImpl(MessageFolderRepository messageFolderRepository, UserService userService, ModelMapper modelMapper) {
        this.messageFolderRepository = messageFolderRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageFolder save(MessageFolder messageFolder) {
        try {
            return messageFolderRepository.save(messageFolder);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateFolderException("Folder already exists for user: " + messageFolder.getUser().getId());
        }
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
    public Optional<MessageFolder> findById(int folderId) {
        return messageFolderRepository.findById(folderId);
    }

    @Override
    public void deleteFolder(int folderId) {
        messageFolderRepository.deleteById(folderId);
    }

    @Override
    public MessageFolderDto createOrUpdateMessageFolder(MessageFolderDto messageFolderDto) {
        User user = userService.findById(messageFolderDto.getOwnerUserId())
                .orElseThrow(() -> new NoSuchUserException("User not found for ID: " + messageFolderDto.getOwnerUserId()));

        MessageFolder parentFolder = null;
        if (messageFolderDto.getParentFolderId() != null) {
            parentFolder = messageFolderRepository.findById(messageFolderDto.getParentFolderId())
                    .orElseThrow(() -> new NoSuchFolderException("Parent folder not found for ID: " + messageFolderDto.getParentFolderId()));
        }

        MessageFolder messageFolder = new MessageFolder();
        if (messageFolderDto.getId() != null) {
            Optional<MessageFolder> existingFolder = messageFolderRepository.findById(messageFolderDto.getId());
            if (existingFolder.isPresent()) {
                messageFolder = existingFolder.get();
                messageFolder.setName(messageFolderDto.getName());
                messageFolder.setParentFolder(parentFolder);
                messageFolder.setUser(user);
            } else {
                messageFolder.setName(messageFolderDto.getName());
                messageFolder.setParentFolder(parentFolder);
                messageFolder.setUser(user);
            }
        }

        MessageFolder savedMessageFolder = messageFolderRepository.save(messageFolder);
        return modelMapper.map(savedMessageFolder, MessageFolderDto.class);
    }
}
