package com.crm.service.serviceImpl;

import com.crm.dao.ClientRepository;
import com.crm.entity.Client;
import com.crm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }
}
