package com.crm.controller;

import com.crm.controller.dto.CompanyDTO;
import com.crm.entity.Company;
import com.crm.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyController(CompanyService companyService, ModelMapper modelMapper){
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> listOfCompanies = companyService.getAllCompanies()
                .stream()
                .map(c -> modelMapper.map(c, CompanyDTO.class))
                .toList();
    return ok(listOfCompanies);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> saveNewCompany(@RequestBody CompanyDTO companyDTO){
        Company company = modelMapper.map(companyDTO, Company.class);
        Company savedCompany = companyService.save(company);

        return ok(modelMapper.map(savedCompany, CompanyDTO.class));
    }

    @PostMapping("/{company-id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("company-id") int companyId, @RequestBody CompanyDTO companyDTO){
        Company updatedCompany = companyService.update(companyId, modelMapper.map(companyDTO, Company.class));

       return ok(modelMapper.map(updatedCompany, CompanyDTO.class));
    }

    @DeleteMapping("/company-id")
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable("company-id") int companyId){
        Company deletedCompany = companyService.delete(companyId);

        return ok(modelMapper.map(deletedCompany, CompanyDTO.class));
    }
}
