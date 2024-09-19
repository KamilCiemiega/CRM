package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDto;
import com.crm.dao.MessageFolderRepository;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageFolderService;
import com.crm.service.MessageService;
import com.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageFolderServiceImpl implements MessageFolderService {

    private final MessageFolderRepository messageFolderRepository;
    private final MessageService messageService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageFolderServiceImpl(MessageFolderRepository messageFolderRepository,MessageService messageService, UserService userService, ModelMapper modelMapper) {
        this.messageFolderRepository = messageFolderRepository;
        this.messageService = messageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MessageFolderDto> findAllMessageFolders() {
        List<MessageFolder> messageFolders = messageFolderRepository.findAll();

        return messageFolders.stream()
                .map(folder -> modelMapper.map(folder, MessageFolderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageFolderDto> findById(int folderId) {
        Optional<MessageFolder> messageFolder = messageFolderRepository.findById(folderId);

        return messageFolder.map(folder -> modelMapper.map(folder, MessageFolderDto.class));
    }

    @Override
    public MessageFolderDto deleteFolder(int folderId) {
        return messageFolderRepository.findById(folderId)
                .filter(folder -> folder.getDefaultFolder() != 1)
                .map(folder -> {
                    messageFolderRepository.deleteById(folderId);
                    return modelMapper.map(folder, MessageFolderDto.class);
                })
                .orElseThrow(() -> new SendMessageExceptionHandlers.deleteDefaultFolderException(
                        "Cannot delete the default folder or folder not found for ID: " + folderId
                ));
    }

    @Override
    public MessageFolderDto createOrUpdateMessageFolder(MessageFolderDto messageFolderDto) {
        User user = userService.findById(messageFolderDto.getOwnerUserId())
                .orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchUserException("User not found for ID: " + messageFolderDto.getOwnerUserId()));

        MessageFolder parentFolder = null;
        if (messageFolderDto.getParentFolderId() != null) {
            parentFolder = messageFolderRepository.findById(messageFolderDto.getParentFolderId())
                    .orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchFolderException("Parent folder not found for ID: " + messageFolderDto.getParentFolderId()));
        }

        MessageFolder messageFolder = new MessageFolder();
        if (messageFolderDto.getId() != null) {
            Optional<MessageFolder> existingFolder = messageFolderRepository.findById(messageFolderDto.getId());
            if (existingFolder.isPresent()) {
                messageFolder = existingFolder.get();
            }
            messageFolder.setName(messageFolderDto.getName());
            messageFolder.setParentFolder(parentFolder);
            messageFolder.setUser(user);
        }

        MessageFolder savedMessageFolder = messageFolderRepository.save(messageFolder);
        return modelMapper.map(savedMessageFolder, MessageFolderDto.class);
    }

    @Override
    public List<MessageDTO> deleteAllMessagesFromFolder(int folderId) {
        Optional<MessageFolder> folder = messageFolderRepository.findById(folderId);

        return folder.map(presentFolder -> {
            List<MessageDTO> listOfDeletedMessages = new ArrayList<>();

            presentFolder.getMessages().forEach(message -> {
                MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
                MessageDTO deletedMessage = messageService.deleteMessage(message.getId());
                listOfDeletedMessages.add(deletedMessage);
            });
            return listOfDeletedMessages;
        }).orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchFolderException("Folder doesn't exist " + folderId));
    }


}
