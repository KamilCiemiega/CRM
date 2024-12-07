package com.crm.service.serviceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.CompanyRepository;
import com.crm.entity.Client;
import com.crm.entity.Company;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public  CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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

        existingCompany.setName(updatedCompany.getName());
        existingCompany.setEmail(updatedCompany.getEmail());
        existingCompany.setPhoneNumber(updatedCompany.getPhoneNumber());
        existingCompany.setAddress(updatedCompany.getAddress());
        existingCompany.setCreatedAt(updatedCompany.getCreatedAt());

        return companyRepository.save(existingCompany);
    }

    @Override
    public Optional<Company> findById(int companyId) {
        return companyRepository.findById(companyId);
    }

}
