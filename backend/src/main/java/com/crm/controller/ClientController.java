package com.crm.controller;

import com.crm.controller.dto.ClientDTO;
import com.crm.controller.dto.CreateClientRequest;
import com.crm.entity.Client;
import com.crm.entity.Company;
import com.crm.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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

        return ok(listOfClientsDTOs);
    }

    @PostMapping("/messages")
    public ResponseEntity<ClientDTO> saveNewClient(
            @RequestParam(value = "message-id", required = false) Integer messageId,
            @RequestBody CreateClientRequest request) {

        Client client = modelMapper.map(request.getClientDTO(), Client.class);
        Company company = modelMapper.map(request.getCompanyDTO(), Company.class);

        Client createdClient = clientService.createClient(messageId, company, client);

        return ok(modelMapper.map(createdClient, ClientDTO.class));
    }

    @PostMapping("/{client-id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("client-id") int clientId, @RequestBody ClientDTO clientDTO){
        Client updatedClient = clientService.updateClient(clientId, modelMapper
                .map(clientDTO, Client.class));
        return ok(modelMapper.map(updatedClient, ClientDTO.class));
    }

    @DeleteMapping("/client-id")
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable("client-id") int clientId){
        Client deletedClient = clientService.delete(clientId);

       return ok(modelMapper.map(deletedClient, ClientDTO.class));
    }


}
