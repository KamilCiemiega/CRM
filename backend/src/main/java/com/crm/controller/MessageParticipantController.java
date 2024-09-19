package com.crm.controller;

import com.crm.controller.dto.MessageParticipantDTO;
import com.crm.service.MessageParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class MessageParticipantController {

    private final MessageParticipantService participantService;

    @Autowired
    public MessageParticipantController(MessageParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<MessageParticipantDTO>> getAllParticipants() {
        List<MessageParticipantDTO> participants = participantService.findAllParticipants();
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageParticipantDTO> createOrUpdateParticipant(@RequestBody MessageParticipantDTO participantDTO) {
        MessageParticipantDTO savedParticipant = participantService.save(participantDTO);
        return new ResponseEntity<>(savedParticipant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable("id") int id) {
        participantService.deleteParticipant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
