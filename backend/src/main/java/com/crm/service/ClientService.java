package com.crm.service;

import com.crm.controller.dto.ClientDTO;
import com.crm.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAllClients();
    Client save(Client client);
    Client updateClient(Integer clientId, Client client);
    Optional<Client> findById(Integer id);
}

