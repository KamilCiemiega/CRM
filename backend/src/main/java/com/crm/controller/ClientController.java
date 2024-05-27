package com.crm.controller;

import com.crm.entity.Client;
import com.crm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/get-clients")
    public ResponseEntity<?> findAllClients(){
        List<Client> listOfClients = clientService.findAllClient();

        if(!listOfClients.isEmpty()){
            return new ResponseEntity<>(listOfClients, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("There is no clients in the database", HttpStatus.NOT_FOUND);
        }
    }
}
