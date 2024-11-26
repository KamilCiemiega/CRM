package com.crm.service.serviceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.CompanyRepository;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.*;
import com.crm.utils.PatchUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CompanyService companyService;
    private final MessageService messageService;
    private final MessageParticipantService messageParticipantService;
    private final MessageRoleService messageRoleService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CompanyRepository companyRepository, CompanyService companyService, MessageService messageService, MessageParticipantService messageParticipantService, MessageRoleService messageRoleService) {
        this.clientRepository = clientRepository;
        this.companyService = companyService;
        this.messageService = messageService;
        this.messageParticipantService = messageParticipantService;
        this.messageRoleService = messageRoleService;
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    private Company findOrCreateCompany(Company company) {
        if (company.getId() != null) {
            return companyService.findById(company.getId())
                    .orElseThrow(() -> new NoSuchEntityException("Company not found with ID: " + company.getId()));
        } else {
            return companyService.save(company);
        }
    }

    private void linkClientToMessage(int messageId, Client client) {
        Message message = messageService.getMessageById(messageId);

        MessageParticipant participant = new MessageParticipant();
        participant.setClient(client);
        participant.setType(MessageParticipant.ParticipantType.CLIENT);
        messageParticipantService.save(participant);

        MessageRole messageRole = new MessageRole();
        messageRole.setParticipant(participant);
        messageRole.setMessage(message);
        messageRole.setStatus(MessageRole.RoleStatus.TO);
        messageRoleService.save(messageRole);
    }

    @Transactional
    @Override
    public Client createClient(int messageId, Company company, Client client) {
        Company finalCompany = findOrCreateCompany(company);

        client.setCompany(finalCompany);
        clientRepository.save(client);

        linkClientToMessage(messageId, client);

        return client;
    }

    @Transactional
    @Override
    public Client updateClient(Integer clientId, Client client) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchEntityException("Client not found with id: " + clientId));

        if (client.getEmail() != null) {
            existingClient.setEmail(client.getEmail());
        }
        if (client.getName() != null) {
            existingClient.setName(client.getName());
        }
        if (client.getSurname() != null) {
            existingClient.setSurname(client.getSurname());
        }
        if (client.getPhone() != null) {
            existingClient.setPhone(client.getPhone());
        }
        if (client.getAddress() != null) {
            existingClient.setAddress(client.getAddress());
        }

        if (client.getCompany() != null && client.getCompany().getId() != null) {
            Company newCompany = companyService.findById(client.getCompany().getId())
                    .orElseThrow(() -> new NoSuchEntityException("Company not found with id: " + client.getCompany().getId()));
            existingClient.setCompany(newCompany);
        }

        return clientRepository.save(existingClient);
    }

}

