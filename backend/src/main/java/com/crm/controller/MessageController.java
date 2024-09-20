package com.crm.controller;

import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<MessageDTO> saveNewMessage(@RequestBody MessageDTO messageDTO) {
        return new ResponseEntity<>(messageService.save(messageDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{message-id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable("message-id") int messageId, @RequestBody MessageDTO messageDTO) {
        MessageDTO updatedMessage = messageService.updateMessage(messageId, messageDTO);
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

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
