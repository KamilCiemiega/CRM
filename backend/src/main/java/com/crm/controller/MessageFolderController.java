package com.crm.controller;

import com.crm.controller.dto.MessageFolderDto;
import com.crm.entity.MessageFolder;
import com.crm.exception.sendMessageExceptionHandlers.SendMessageExceptionHandlers;
import com.crm.service.MessageFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/message-folders")
public class MessageFolderController {

    private final MessageFolderService messageFolderService;

    @Autowired
    public MessageFolderController(MessageFolderService messageFolderService) {
        this.messageFolderService = messageFolderService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<MessageFolderDto> createOrUpdateFolder(@RequestBody MessageFolderDto messageFolderDto) {
        try {
            MessageFolderDto savedMessageFolderDto = messageFolderService.createOrUpdateMessageFolder(messageFolderDto);
            return new ResponseEntity<>(savedMessageFolderDto, HttpStatus.CREATED);
        } catch ( SendMessageExceptionHandlers.NoSuchUserException | SendMessageExceptionHandlers.NoSuchFolderException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<MessageFolder>> getFolders(){
        List<MessageFolder> listOfMessageFolders = messageFolderService.findAllMessageFolders();

        return new ResponseEntity<>(listOfMessageFolders, HttpStatus.OK);
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
