package com.crm.service.serviceImpl;

import com.crm.controller.dto.ClientDTO;
import com.crm.dao.ClientRepository;
import com.crm.entity.Client;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.ClientService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    @Override
    public Client updateClient(Integer clientId, Client client) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchEntityException("Client not found with id: " + clientId));

        return clientRepository.save(existingClient);
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }
}

