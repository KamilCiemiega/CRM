package com.crm.service;

import com.crm.controller.dto.ClientDTO;
import com.crm.entity.Client;
import com.crm.entity.Company;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAllClients();
    Client createClient(int messageId, Company company, Client client);
    Client updateClient(Integer clientId, Client client);
}

