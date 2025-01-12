package com.crm.controller;

import com.crm.controller.dto.ClientDTO;
import com.crm.controller.dto.message.MessageParticipantDTO;
import com.crm.controller.dto.user.UserDTO;
import com.crm.entity.Client;
import com.crm.entity.MessageParticipant;
import com.crm.entity.User;
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

    @GetMapping("/by-type-and-id")
    public ResponseEntity<MessageParticipantDTO> getParticipantByTypeAndId(
            @RequestParam MessageParticipant.ParticipantType type,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer clientId) {

        MessageParticipant messageParticipant = messageParticipantService.findUserOrClientById(type, userId, clientId);
        MessageParticipantDTO messageParticipantDTO = modelMapper.map(messageParticipant, MessageParticipantDTO.class);

        return new ResponseEntity<>(messageParticipantDTO, HttpStatus.OK);
    }

    @GetMapping("/by-id")
    public ResponseEntity<Object> getUserOrClientByParticipantId(@RequestParam Integer participantId) {
        Object participant = messageParticipantService.findUserOrClientByParticipantId(participantId);

        if (participant instanceof User) {
            UserDTO userDTO = modelMapper.map(participant, UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } else if (participant instanceof Client) {
            ClientDTO clientDTO = modelMapper.map(participant, ClientDTO.class);
            return ResponseEntity.ok(clientDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }
    }

}
