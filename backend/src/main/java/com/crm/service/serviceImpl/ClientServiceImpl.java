package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final MessageRepository messageRepository;
    private final MessageParticipantRepository messageParticipantRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             CompanyRepository companyRepository,
                             MessageRepository messageRepository,
                             MessageParticipantRepository messageParticipantRepository) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
        this.messageRepository = messageRepository;
        this.messageParticipantRepository = messageParticipantRepository;
    }

    @Override
    public Optional<Client> findById(int clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    private Company findOrCreateCompany(Company company) {
        if (company.getId() != null) {
            return companyRepository.findById(company.getId())
                    .orElseThrow(() -> new NoSuchEntityException("Company not found with ID: " + company.getId()));
        } else {
            return companyRepository.save(company);
        }
    }

    private void linkClientToMessage(int messageId, Client client) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchEntityException("Can't find message with id " + messageId));

        MessageParticipant participant = new MessageParticipant();
        participant.setClient(client);
        participant.setType(MessageParticipant.ParticipantType.CLIENT);
        messageParticipantRepository.save(participant);

        message.getMessageRoles().stream()
                .filter(role -> Objects.equals(role.getEmail(), client.getEmail()))
                .forEach(role -> {
                    role.setEmail(null);
                    role.setParticipant(participant);
                });
    }

    @Transactional
    @Override
    public Client createClient(Integer messageId, Company company, Client client) {
        Company finalCompany = findOrCreateCompany(company);

        client.setCompany(finalCompany);
        clientRepository.save(client);

        if(messageId != null){
            linkClientToMessage(messageId, client);
        }

        return client;
    }

    @Transactional
    @Override
    public Client updateClient(Integer clientId, Client client) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchEntityException("Client not found with id: " + clientId));

        ofNullable(client.getEmail()).ifPresent(existingClient::setEmail);
        ofNullable(client.getName()).ifPresent(existingClient::setEmail);

        if (client.getCompany() != null && client.getCompany().getId() != null) {
            Company newCompany = companyRepository.findById(client.getCompany().getId())
                    .orElseThrow(() -> new NoSuchEntityException("Company not found with id: " + client.getCompany().getId()));
            existingClient.setCompany(newCompany);
        }

        return clientRepository.save(existingClient);
    }

    @Transactional
    @Override
    public Client delete(int clientId) {
        Client clientToDelete = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchEntityException("Client not found for ID: " + clientId));

        clientRepository.delete(clientToDelete);

        return  clientToDelete;
    }
}

