package com.crm.controller;

import com.crm.controller.dto.MessageFolderDto;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message-folders")
public class MessageFolderController {
    private final MessageFolderService messageFolderService;

    @Autowired
    public MessageFolderController(MessageFolderService messageFolderService) {
        this.messageFolderService = messageFolderService;
    }

    @GetMapping
    public ResponseEntity<List<MessageFolderDto>> getFolders() {
        List<MessageFolderDto> listOfMessageFolders = messageFolderService.findAllMessageFolders();

        if (listOfMessageFolders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listOfMessageFolders, HttpStatus.OK);
        }
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

    @Transactional
    @DeleteMapping("/{folder-id}")
    public ResponseEntity<MessageFolderDto> deleteFolder(@PathVariable("folder-id") int folderId) {
        return new ResponseEntity<>(messageFolderService.deleteFolder(folderId),HttpStatus.OK);
    }

//    @Transactional
//    @DeleteMapping("/{folder-id}/messages")
//    public ResponseEntity<List<Message>> deleteAllMessagesFromFolder(@PathVariable("folder-id") int folderId) {
//        return new ResponseEntity<>(messageFolderService.deleteAllMessagesFromFolder(folderId), HttpStatus.OK);
//    }
}
