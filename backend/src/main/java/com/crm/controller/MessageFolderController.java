package com.crm.controller;

import com.crm.controller.dto.MessageFolderDto;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.service.MessageFolderService;
import com.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// TODO use kebab-case for URLs (https://www.freecodecamp.org/news/snake-case-vs-camel-case-vs-pascal-case-vs-kebab-case-whats-the-difference/)
// so api/message-folders
@RequestMapping("/api/messageFolders") 
public class MessageFolderController {

    private final MessageFolderService messageFolderService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageFolderController(MessageFolderService messageFolderService, UserService userService, ModelMapper modelMapper) {
        this.messageFolderService = messageFolderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create") // just "/". We are doing POST to /api/message-folders resource
    public ResponseEntity<MessageFolder> createFolderIfNotExist(@RequestBody MessageFolderDto messageFolderDto) {

        MessageFolder messageFolder = modelMapper.map(messageFolderDto, MessageFolder.class);

        // this is business logic (query for user, link it, save it) This should be INSIDE service method call.
        // then service should just throw a new type of a exception NoSuchUserException extends RuntimeException. 
        // For now just leave this throwing exception. Later we will add proper error handling 
        // like is described here: https://www.baeldung.com/exception-handling-for-rest-with-spring
        Optional<User> user = userService.findById(messageFolderDto.getOwnerUserId());
        if (user.isPresent()) {
            messageFolder.setUser(user.get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (messageFolderDto.getParentFolderId() != null) {
            Optional<MessageFolder> parentFolder = messageFolderService.findById(messageFolderDto.getParentFolderId());
            if (parentFolder.isPresent()) {
                messageFolder.setParentFolder(parentFolder.get());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        // This is not done this way. The way to do this is to create a UNIQUE index on a pair of fields 
        // (user, name). See https://www.baeldung.com/jpa-indexes
        Optional<MessageFolder> existingMessageFolder = messageFolderService.findByNameAndUser(
                messageFolderDto.getName(),
                messageFolder.getUser()
        );

        if (existingMessageFolder.isPresent()) {
            return new ResponseEntity<>(existingMessageFolder.get(), HttpStatus.CONFLICT);
        } else {
            MessageFolder savedMessageFolder = messageFolderService.save(messageFolder);
            return new ResponseEntity<>(savedMessageFolder, HttpStatus.CREATED); // should return DTO, not a model 
        }
    }
    @GetMapping("/all") // just GET /api/message-folders - no need for ALL
    public ResponseEntity<List<MessageFolder>> getFolders(){
        List<MessageFolder> listOfMessageFolders = messageFolderService.findAllMessageFolders();

        return  new ResponseEntity<>(listOfMessageFolders, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{folder-id}")
    public ResponseEntity<MessageFolder> deleteFolder(@PathVariable("folder-id") int folderId) {
        Optional<MessageFolder> folder = messageFolderService.findById(folderId);
        if (folder.isPresent()) {
            MessageFolder folderToDelete = folder.get();
            messageFolderService.deleteFolder(folderId);
            return new ResponseEntity<>(folderToDelete, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
