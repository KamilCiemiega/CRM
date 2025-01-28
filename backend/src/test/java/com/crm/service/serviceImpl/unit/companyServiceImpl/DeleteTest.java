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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCompanyTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl underTest;

    @Test
    void shouldDeleteCompany() {
        // given
        Integer companyId = 1;
        Company company = new Company();
        company.setId(companyId);

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        doNothing().when(companyRepository).delete(company);

        // when
        underTest.delete(companyId);

        // then
        verify(companyRepository, times(1)).findById(companyId);
        verify(companyRepository, times(1)).delete(company);
    }
}
