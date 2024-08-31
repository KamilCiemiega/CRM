package com.crm.controller;

import com.crm.entity.MessageFolder;
import com.crm.service.MessageFolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/messageFolders")
public class MessageFolderController {

    private final MessageFolderService messageFolderService;
    private static final Logger logger = LoggerFactory.getLogger(MessageFolderController.class);

    @Autowired
    public MessageFolderController(MessageFolderService messageFolderService) {
        this.messageFolderService = messageFolderService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageFolder> createMessageFolderIfNotExist(@RequestBody MessageFolder messageFolder) {
        Optional<MessageFolder> existingMessageFolder = messageFolderService.findByNameAndOwner(
                messageFolder.getName(),
                messageFolder.getUser()
        );

        if (existingMessageFolder.isPresent()) {
            return new ResponseEntity<>(existingMessageFolder.get(), HttpStatus.CONFLICT);
        } else {
            MessageFolder savedMessageFolder = messageFolderService.save(messageFolder);
            return new ResponseEntity<>(savedMessageFolder, HttpStatus.CREATED);
        }
    }
}
