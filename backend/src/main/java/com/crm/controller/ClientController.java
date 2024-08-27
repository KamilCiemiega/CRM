package com.crm.controller;

import com.crm.entity.Client;
import com.crm.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get-clients")
    public ResponseEntity<List<Client>> findAllClients(){
        List<Client> listOfClients = clientService.findAllClients();

        if(!listOfClients.isEmpty()){
            return new ResponseEntity<>(listOfClients, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/clients")
    public ResponseEntity<String> saveClient(@RequestBody Client client){
        clientService.save(client);

        return ResponseEntity.ok("Client saved successfully");
    }

    @Transactional
    @PutMapping("/{clientId}")
    public ResponseEntity<String> updateClient(@PathVariable("client-id") int clientId, @RequestBody Client client){
        Optional<Client> clientOptional = clientService.findById(clientId);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }

        Client existingClient = clientOptional.get();
        modelMapper.map(client, existingClient);

        clientService.save(existingClient);

        return ResponseEntity.ok("Client updated successfully");
    }
}
