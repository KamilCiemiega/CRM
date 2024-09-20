package com.crm.controller;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDTO;
import com.crm.service.MessageFolderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<MessageFolderDTO>> getFolders() {
        List<MessageFolderDTO> listOfMessageFolders = messageFolderService.findAllMessageFolders();

        if (listOfMessageFolders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listOfMessageFolders, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<MessageFolderDTO> saveNewFolderMessage(@RequestBody MessageFolderDTO messageFolderDTO) {
        return new ResponseEntity<>(messageFolderService.save(messageFolderDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{message-id}")
    public ResponseEntity<MessageFolderDTO> updateFolderMessage(@PathVariable("message-id") int messageId, @RequestBody MessageFolderDTO messageFolderDTO) {
        MessageFolderDTO updatedFolder = messageFolderService.updateMessageFolder(messageId, messageFolderDTO);
        return new ResponseEntity<>(updatedFolder, HttpStatus.OK);
    }

    @DeleteMapping("/{folder-id}")
    public ResponseEntity<MessageFolderDTO> deleteFolder(@PathVariable("folder-id") int folderId) {
        return new ResponseEntity<>(messageFolderService.deleteFolder(folderId),HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{folder-id}/messages")
    public ResponseEntity<List<MessageDTO>> deleteAllMessagesFromFolder(@PathVariable("folder-id") int folderId) {
        return new ResponseEntity<>(messageFolderService.deleteAllMessagesFromFolder(folderId), HttpStatus.OK);
    }
}
