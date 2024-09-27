package com.crm.controller;

import com.crm.controller.dto.ClientDTO;
import com.crm.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> findAllClients(){
        List<ClientDTO> listOfClients = clientService.findAllClients();
        return ResponseEntity.ok(listOfClients);
    }

    @PostMapping("/clients")
    public ResponseEntity<String> saveClient(@RequestBody ClientDTO clientDTO){
        clientService.save(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client saved successfully");
    }

    @PostMapping("/clients/{clientId}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("clientId") int clientId, @RequestBody ClientDTO clientDTO){
        ClientDTO updatedClient = clientService.updateClient(clientId, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }
}
