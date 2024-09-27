package com.crm.service.serviceImpl;

import com.crm.controller.dto.ClientDTO;
import com.crm.dao.ClientRepository;
import com.crm.entity.Client;
import com.crm.exception.NoSuchClientException;
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
    public void save(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        clientRepository.save(client);
    }

    @Override
    public List<ClientDTO> findAllClients() {
        List<Client> listOfClients = clientRepository.findAll();
        return listOfClients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public ClientDTO updateClient(Integer clientId, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchClientException("Client not found with id: " + clientId));

        modelMapper.map(clientDTO, existingClient);
        clientRepository.save(existingClient);

        return modelMapper.map(existingClient, ClientDTO.class);
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }
}

