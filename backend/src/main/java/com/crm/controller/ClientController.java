package com.crm.controller;

import com.crm.controller.dto.ClientDTO;
import com.crm.entity.Client;
import com.crm.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> findAllClients(){
        List<Client> listOfClients = clientService.findAllClients();
        List<ClientDTO> listOfClientsDTOs = listOfClients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList();

        return ResponseEntity.ok(listOfClientsDTOs);
    }

    @PostMapping()
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO){
        Client client = clientService.save(modelMapper.map(clientDTO, Client.class));
        return new ResponseEntity<>(modelMapper.map(client, ClientDTO.class), HttpStatus.OK);
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable("clientId") int clientId, @RequestBody ClientDTO clientDTO){
        Client updatedClient = clientService.updateClient(clientId, modelMapper.map(clientDTO, Client.class));
        return ResponseEntity.ok(updatedClient);
    }
}
