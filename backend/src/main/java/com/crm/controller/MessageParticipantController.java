package com.crm.controller;

import com.crm.controller.dto.MessageParticipantDTO;
import com.crm.entity.MessageParticipant;
import com.crm.service.MessageParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/message-participant")
public class MessageParticipantController {

    private final ModelMapper modelMapper;
    private final MessageParticipantService messageParticipantService;

    @Autowired
    public MessageParticipantController(ModelMapper modelMapper, MessageParticipantService messageParticipantService){
        this.modelMapper = modelMapper;
        this.messageParticipantService = messageParticipantService;
    }

    @GetMapping()
    public ResponseEntity<MessageParticipantDTO> getParticipantByTypeAndId(
            @RequestParam MessageParticipant.ParticipantType type,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer clientId) {

        MessageParticipant messageParticipant = messageParticipantService.findUserOrClientById(type, userId, clientId);
        MessageParticipantDTO messageParticipantDTO = modelMapper.map(messageParticipant, MessageParticipantDTO.class);

        return new ResponseEntity<>(messageParticipantDTO, HttpStatus.OK);
    }
}
