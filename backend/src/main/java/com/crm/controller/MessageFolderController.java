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

    @PostMapping("/create")
    public ResponseEntity<MessageFolder> createFolderIfNotExist(@RequestBody MessageFolderDto messageFolderDto) {

        MessageFolder messageFolder = modelMapper.map(messageFolderDto, MessageFolder.class);

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

        Optional<MessageFolder> existingMessageFolder = messageFolderService.findByNameAndUser(
                messageFolderDto.getName(),
                messageFolder.getUser()
        );

        if (existingMessageFolder.isPresent()) {
            return new ResponseEntity<>(existingMessageFolder.get(), HttpStatus.CONFLICT);
        } else {
            MessageFolder savedMessageFolder = messageFolderService.save(messageFolder);
            return new ResponseEntity<>(savedMessageFolder, HttpStatus.CREATED);
        }
    }
    @GetMapping("/all")
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
