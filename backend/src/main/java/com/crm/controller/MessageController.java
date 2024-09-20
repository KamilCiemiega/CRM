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

    @Transactional // should be enough to put TX around service method, not a controller method
    @PostMapping("/message")
    public ResponseEntity<MessageDTO> CreateOrUpdateMessage(@RequestBody MessageDTO messageDTO) {
        return new ResponseEntity<>(messageService.CreateOrUpdateExistingMessage(messageDTO), HttpStatus.CREATED);
    }

    /**
     * Current state:
     *
     * POST /api/messages/message - update / create
     *
     * Should be:
     *
     * GET /api/messages - get all messages (list)
     * GET /api/messages/:id - get single message
     * POST /api/messages/:id - update message
     * DELETE /api/message/:id - delete message
     * POST /api/messages - create message
     * POST /api/messages/:id - update or create (!!!!)

     */

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
