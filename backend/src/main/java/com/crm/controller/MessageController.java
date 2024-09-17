package com.crm.controller;

import com.crm.Enum.MessageSortType;
import com.crm.entity.Message;
import com.crm.exception.SendMessageExceptionHandlers;
import com.crm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return  messageService.findAllMessage();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") int messageId) {
        Optional<Message> message = messageService.findById(messageId);
        if(message.isPresent()){
            Message findedMessage = message.get();
            return new ResponseEntity<>(findedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Message> createOrUpdateMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.createOrUpdateMessage(message);
            return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
        } catch (SendMessageExceptionHandlers.NoSuchMessageException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @DeleteMapping("/{message-id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable("message-id") int messageId){
        return new ResponseEntity<>(messageService.deleteMessage(messageId), HttpStatus.OK);
    }

    @GetMapping("/folders/{folderId}/messages")
    public ResponseEntity<List<Message>> getSortedMessagesByType(
            @PathVariable Integer folderId,
            @RequestParam MessageSortType sortType,
            @RequestParam String orderType) {
        List<Message> messages = messageService.getSortedMessages(folderId, sortType, orderType);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
