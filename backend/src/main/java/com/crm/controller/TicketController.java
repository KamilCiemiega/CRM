package com.crm.controller;

import com.crm.controller.dto.ticket.SimpleTicketDTO;
import com.crm.controller.dto.ticket.TicketDTO;
import com.crm.entity.Ticket;
import com.crm.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final ModelMapper modelMapper;
    private final TicketService ticketService;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TicketController.class);

    @Autowired
    public TicketController(ModelMapper modelMapper, TicketService ticketService) {
        this.modelMapper = modelMapper;
        this.ticketService = ticketService;
    }

    @GetMapping()
    public ResponseEntity<List<TicketDTO>> getAllTickets(){
        List<Ticket> listOfTickets = ticketService.getAllTickets();
        List<TicketDTO> listOfTicketDTOs = listOfTickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketDTO.class))
                .toList();

        return ok(listOfTicketDTOs);
    }

    @PostMapping()
    public ResponseEntity<TicketDTO> saveNewTicket(@RequestBody TicketDTO ticketDTO){
        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        Ticket savedTicket = ticketService.save(ticket);

        return ok(modelMapper.map(savedTicket, TicketDTO.class));
    }

    @PostMapping("/{ticket-id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable("ticket-id") int ticketId, @RequestBody TicketDTO ticketDTO){
        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        Ticket updatedTicket = ticketService.updateTicket(ticketId, ticket);
        return ok(modelMapper.map(updatedTicket, TicketDTO.class));
    }

    @DeleteMapping("/{ticked-id}")
    public ResponseEntity<SimpleTicketDTO> deleteTicket(@PathVariable("ticked-id") int tickedId){
        Ticket deletedTicket = ticketService.deleteTicket(tickedId);
        return ok(modelMapper.map(deletedTicket, SimpleTicketDTO.class));
    }

}
