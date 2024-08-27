package com.crm.service;

import com.crm.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAllClients();
    void save(Client client);
    Optional<Client> findById(Integer id);
}
