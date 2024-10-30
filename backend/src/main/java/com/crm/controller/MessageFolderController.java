package com.crm.controller;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDTO;
import com.crm.entity.Message;
import com.crm.entity.MessageFolder;
import com.crm.service.MessageFolderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/message-folders")
public class MessageFolderController {
    private final MessageFolderService messageFolderService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageFolderController(MessageFolderService messageFolderService, ModelMapper modelMapper) {
        this.messageFolderService = messageFolderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageFolderDTO>> getFolders() {
        List<MessageFolder> listOfMessageFolders = messageFolderService.findAllMessageFolders();
        List<MessageFolderDTO> listOfMessageDTOs = listOfMessageFolders.stream()
                .map(folder -> modelMapper.map(folder, MessageFolderDTO.class))
                .toList();

        if (listOfMessageFolders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listOfMessageDTOs, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<MessageFolderDTO> saveNewFolderMessage(@RequestBody MessageFolderDTO messageFolderDTO) {
        MessageFolder savedFolderMessage = messageFolderService.save(modelMapper.map(messageFolderDTO, MessageFolder.class));

        return new ResponseEntity<>(modelMapper.map(savedFolderMessage, MessageFolderDTO.class), HttpStatus.CREATED);
    }

    @PostMapping("/{message-id}")
    public ResponseEntity<MessageFolderDTO> updateMessageFolder(@PathVariable("message-id") int folderId, @RequestBody MessageFolderDTO messageFolderDTO) {
        MessageFolder updatedFolder = messageFolderService.updateMessageFolder(folderId, modelMapper.map(messageFolderDTO, MessageFolder.class));
        return new ResponseEntity<>(modelMapper.map(updatedFolder, MessageFolderDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{folder-id}")
    public ResponseEntity<MessageFolderDTO> deleteFolder(@PathVariable("folder-id") int folderId) {
        MessageFolder deletedFolder = messageFolderService.deleteFolder(folderId);
        return new ResponseEntity<>(modelMapper.map(deletedFolder, MessageFolderDTO.class),HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{folder-id}/messages")
    public ResponseEntity<List<MessageDTO>> deleteAllMessagesFromFolder(@PathVariable("folder-id") int folderId) {
        List<Message> deletedMessages = messageFolderService.deleteAllMessagesFromFolder(folderId);
        List<MessageDTO> messageDTOs = deletedMessages.stream()
                .map(m -> modelMapper.map(m, MessageDTO.class))
                .toList();

        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }
}
