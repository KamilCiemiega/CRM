package com.crm.controller;

import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.service.MessageFolderService;
import com.crm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messageFolders")
public class MessageFolderController {

    private final MessageFolderService messageFolderService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MessageFolderController.class);

    @Autowired
    public MessageFolderController(MessageFolderService messageFolderService, UserService userService) {
        this.messageFolderService = messageFolderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageFolder> createMessageFolderIfNotExist(@RequestBody MessageFolder messageFolder) {
        Optional<MessageFolder> existingMessageFolder = messageFolderService.findByNameAndUser(
                messageFolder.getName(),
                messageFolder.getUser()
        );

        if (existingMessageFolder.isPresent()) {
            return new ResponseEntity<>(existingMessageFolder.get(), HttpStatus.CONFLICT);
        } else {
            User user = messageFolder.getUser();
            if (user != null) {
                if (user.getId() == null || userService.findById(user.getId()).isEmpty()) {
                    user = userService.save(user);
                    messageFolder.setUser(user);
                }
            }

            if (messageFolder.getParentFolder() != null) {
                Optional<MessageFolder> parentFolder = messageFolderService.findById(messageFolder.getParentFolder().getId());
                if (parentFolder.isPresent()) {
                    messageFolder.setParentFolder(parentFolder.get());
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            MessageFolder savedMessageFolder = messageFolderService.save(messageFolder);
            return new ResponseEntity<>(savedMessageFolder, HttpStatus.CREATED);
        }
    }
    @GetMapping("all")
    public ResponseEntity<List<MessageFolder>> getMessageFolders(){
        List<MessageFolder> listOfMessageFolders = messageFolderService.findAllMessageFolders();

        return  new ResponseEntity<>(listOfMessageFolders, HttpStatus.OK);
    }


}
