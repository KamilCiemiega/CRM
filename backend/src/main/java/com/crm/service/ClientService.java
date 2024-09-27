package com.crm.service;

import com.crm.controller.dto.ClientDTO;
import com.crm.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDTO> findAllClients();
    void save(ClientDTO clientDTO);
    ClientDTO updateClient(Integer clientId, ClientDTO clientDTO);
    Optional<Client> findById(Integer id);
}

