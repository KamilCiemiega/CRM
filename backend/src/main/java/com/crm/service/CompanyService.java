package com.crm.service;

import com.crm.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company save(Company company);
    Company update(int companyId, Company company);
    Company delete(int companyId);
}
