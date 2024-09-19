package com.crm.controller;

import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
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
    public List<MessageDTO> getAllMessages() {
        return  messageService.findAllMessage();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable("id") int messageId) {
        Optional<MessageDTO> message = messageService.findById(messageId);
        if(message.isPresent()){
            MessageDTO foundMessage = message.get();
            return new ResponseEntity<>(foundMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<MessageDTO> createOrUpdateMessage(@RequestBody MessageDTO messageDTO) {
        try {
            MessageDTO savedMessage = messageService.createOrUpdateMessage(messageDTO);
            return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
        } catch (SendMessageExceptionHandlers.NoSuchMessageException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping("/message")
    public ResponseEntity<MessageDTO> createNewMessage(@RequestBody MessageDTO messageDTO){

    }

    @Transactional
    @PostMapping("/message/{message-id}")
    public ResponseEntity<MessageDTO> createNewMessage(@PathVariable("message-id") int messageId){

    }

    @Transactional
    @DeleteMapping("/{message-id}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable("message-id") int messageId){
        return new ResponseEntity<>(messageService.deleteMessage(messageId), HttpStatus.OK);
    }

    @GetMapping("/folders/{folderId}/messages")
    public ResponseEntity<List<MessageDTO>> getSortedMessages(
            @PathVariable Integer folderId,
            @RequestParam String sortType,
            @RequestParam String orderType) {

        MessageSortType messageSortType = MessageSortType.valueOf(sortType.toUpperCase());
        List<MessageDTO> messages = messageService.getSortedMessages(folderId, messageSortType, orderType);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
