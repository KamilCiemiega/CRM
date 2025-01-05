package com.crm.service.serviceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.CompanyRepository;
import com.crm.entity.Client;
import com.crm.entity.Company;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.ClientService;
import com.crm.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public  CompanyServiceImpl(CompanyRepository companyRepository, ClientRepository clientRepository) {
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional
    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    @Override
    public Company update(int companyId, Company updatedCompany) {
        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchEntityException("Company not found for ID: " + companyId));

        Optional.ofNullable(updatedCompany.getName())
                .ifPresent(existingCompany::setName);
        Optional.ofNullable(updatedCompany.getEmail())
                .ifPresent(existingCompany::setEmail);
        existingCompany.setPhoneNumber(updatedCompany.getPhoneNumber());
        existingCompany.setAddress(updatedCompany.getAddress());

        if (updatedCompany.getClients() != null) {
            List<Client> newListOfClients = new ArrayList<>();

            for (Client client : updatedCompany.getClients()) {
                if (client.getId() != null) {
                    Client existingClient = clientRepository.findById(client.getId())
                            .orElseThrow(() -> new NoSuchEntityException("Client not found for ID: " + client.getId()));

                    existingClient.setCompany(existingCompany);
                    newListOfClients.add(existingClient);
                }
            }

            List<Client> existingClients = new ArrayList<>(existingCompany.getClients());
            existingClients.stream()
                    .filter(client -> !newListOfClients.contains(client))
                    .forEach(client -> client.setCompany(null));

            existingCompany.setClients(newListOfClients);
        }

        return companyRepository.save(existingCompany);

    }

    @Transactional
    @Override
    public Company delete(int companyId) {
        Company companyToDelete = companyRepository.findById(companyId)
                .orElseThrow(() -> new NoSuchEntityException("Company not found for ID: " + companyId));

        for (Client client : companyToDelete.getClients()) {
            client.setCompany(null);
        }
        companyRepository.delete(companyToDelete);

        return companyToDelete;
    }

}
