package com.crm.service.serviceImpl.unit.companyServiceImpl;

import com.crm.dao.CompanyRepository;
import com.crm.entity.Company;
import com.crm.service.serviceImpl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCompaniesTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl underTest;

    @Test
    void shouldReturnAllCompanies() {
        // given
        Company company1 = new Company();
        company1.setName("Company 1");

        Company company2 = new Company();
        company2.setName("Company 2");

        when(companyRepository.findAll()).thenReturn(List.of(company1, company2));

        // when
        List<Company> result = underTest.getAllCompanies();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).contains(company1, company2);
        verify(companyRepository, times(1)).findAll();
    }
}