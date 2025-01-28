package com.crm.service.serviceImpl.unit.companyServiceImpl;

import com.crm.dao.CompanyRepository;
import com.crm.entity.Company;
import com.crm.service.serviceImpl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class  SaveTest{

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl underTest;

    @Test
    void shouldCreateCompany() {
        // given
        Company company = new Company();
        company.setName("Test Company");

        when(companyRepository.save(company)).thenReturn(company);

        // when
        Company createdCompany = underTest.save(company);

        // then
        assertThat(createdCompany).isNotNull();
        assertThat(createdCompany.getName()).isEqualTo("Test Company");
        verify(companyRepository, times(1)).save(company);
    }
}
