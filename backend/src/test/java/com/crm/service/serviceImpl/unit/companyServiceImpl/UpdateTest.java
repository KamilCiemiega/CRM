package com.crm.service.serviceImpl.unit.companyServiceImpl;

import com.crm.dao.CompanyRepository;
import com.crm.entity.Company;
import com.crm.service.serviceImpl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCompanyTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl underTest;

    @Test
    void shouldUpdateCompany() {
        // given
        Integer companyId = 1;
        Company existingCompany = new Company();
        existingCompany.setId(companyId);
        existingCompany.setName("Old Name");

        Company updatedCompany = new Company();
        updatedCompany.setName("New Name");

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(existingCompany)).thenReturn(existingCompany);

        // when
        Company result = underTest.update(companyId, updatedCompany);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("New Name");
        verify(companyRepository, times(1)).findById(companyId);
        verify(companyRepository, times(1)).save(existingCompany);
    }

}