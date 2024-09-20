package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDTO;
import com.crm.dao.MessageFolderRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.exception.SendMessageExceptionHandlers;
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
    public List<MessageFolderDTO> findAllMessageFolders() {
        List<MessageFolder> messageFolders = messageFolderRepository.findAll();

        return messageFolders.stream()
                .map(folder -> modelMapper.map(folder, MessageFolderDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public MessageFolderDTO save(MessageFolderDTO messageFolderDTO) {
        MessageFolder savedMessageFolder = messageFolderRepository.save(modelMapper.map(messageFolderDTO, MessageFolder.class));
        return modelMapper.map(savedMessageFolder, MessageFolderDTO.class);
    }

    @Override
    @Transactional
    public MessageFolderDTO updateMessageFolder(int folderId, MessageFolderDTO messageFolderDTO) {
        MessageFolder existingFolder = messageFolderRepository.findById(folderId)
                .orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchMessageException("Folder not found for ID: " + folderId));

        existingFolder.setName(messageFolderDTO.getName());

        if (messageFolderDTO.getParentFolderId() != null) {
            MessageFolder parentFolder = messageFolderRepository.findById(messageFolderDTO.getParentFolderId())
                    .orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchMessageException("Parent folder not found for ID: " + messageFolderDTO.getParentFolderId()));
            existingFolder.setParentFolder(parentFolder);
        } else {
            existingFolder.setParentFolder(null);
        }

        if (messageFolderDTO.getOwnerUserId() != null) {
            User user = userRepository.findById(messageFolderDTO.getOwnerUserId())
                    .orElseThrow(() -> new SendMessageExceptionHandlers.NoSuchMessageException("User not found for ID: " + messageFolderDTO.getOwnerUserId()));
            existingFolder.setUser(user);
        }
        existingFolder.setDefaultFolder(messageFolderDTO.getDefaultFolder());

        MessageFolder updatedFolder = messageFolderRepository.save(existingFolder);

        return modelMapper.map(updatedFolder, MessageFolderDTO.class);
    }

    @Override
    public Optional<MessageFolderDTO> findById(int folderId) {
        Optional<MessageFolder> messageFolder = messageFolderRepository.findById(folderId);

        return messageFolder.map(folder -> modelMapper.map(folder, MessageFolderDTO.class));
    }

    @Override
    @Transactional
    public MessageFolderDTO deleteFolder(int folderId) {
        return messageFolderRepository.findById(folderId)
                .filter(folder -> folder.getDefaultFolder() != 1)
                .map(folder -> {
                    messageFolderRepository.deleteById(folderId);
                    return modelMapper.map(folder, MessageFolderDTO.class);
                })
                .orElseThrow(() -> new SendMessageExceptionHandlers.deleteDefaultFolderException(
                        "Cannot delete the default folder or folder not found for ID: " + folderId
                ));
    }

    @Override
    @Transactional
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
