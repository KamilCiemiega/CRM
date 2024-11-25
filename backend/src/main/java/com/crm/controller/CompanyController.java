package com.crm.controller;

import com.crm.controller.dto.CompanyDTO;
import com.crm.entity.Company;
import com.crm.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return new ResponseEntity<>(listOfCompanies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> saveNewCompany(@RequestBody CompanyDTO companyDTO){
        Company company = modelMapper.map(companyDTO, Company.class);
        Company savedCompany = companyService.save(company);

        return new ResponseEntity<>(modelMapper.map(savedCompany, CompanyDTO.class), HttpStatus.OK);
    }

    @PostMapping("/{company-id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("company-id") int companyId, @RequestBody CompanyDTO companyDTO){
        Company updatedCompany = companyService.update(companyId, modelMapper.map(companyDTO, Company.class));
        return new ResponseEntity<>(modelMapper.map(updatedCompany, CompanyDTO.class), HttpStatus.OK);
    }
}
