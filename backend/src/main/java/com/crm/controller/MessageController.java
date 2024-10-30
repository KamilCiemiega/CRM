package com.crm.controller;

import com.crm.entity.Message;
import com.crm.enums.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        List<MessageDTO> listOfMessage = messageService.findAllMessage()
                .stream()
                .map(m -> modelMapper.map(m, MessageDTO.class))
                .toList();

        return new ResponseEntity<>(listOfMessage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable("id") int messageId) {
        return new ResponseEntity<>(modelMapper.map(messageService.getMessageById(messageId), MessageDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDTO> saveNewMessage(@RequestBody MessageDTO messageDTO) {
        Message savedMessage = messageService.save(modelMapper.map(messageDTO, Message.class));

        return new ResponseEntity<>(modelMapper.map(savedMessage, MessageDTO.class), HttpStatus.CREATED);
    }

    @PostMapping("/{message-id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable("message-id") int messageId, @RequestBody MessageDTO messageDTO) {
        Message updatedMessage = messageService.updateMessage(messageId, modelMapper.map(messageDTO, Message.class));
        return new ResponseEntity<>(modelMapper.map(updatedMessage, MessageDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{message-id}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable("message-id") int messageId){
        return new ResponseEntity<>(modelMapper.map(messageService.deleteMessage(messageId), MessageDTO.class), HttpStatus.OK);
    }

    @GetMapping("/folders/{folderId}/messages")
    public ResponseEntity<List<MessageDTO>> getSortedMessages(
            @PathVariable Integer folderId,
            @RequestParam String sortType,
            @RequestParam String orderType) {

        MessageSortType messageSortType = MessageSortType.valueOf(sortType.toUpperCase());
        List<Message> messages = messageService.getSortedMessages(folderId, messageSortType, orderType);
        List<MessageDTO> messageDTOs = messages.stream()
                .map(m -> modelMapper.map(m, MessageDTO.class))
                .toList();

        return new ResponseEntity<>(messageDTOs, HttpStatus.OK);
    }
}
