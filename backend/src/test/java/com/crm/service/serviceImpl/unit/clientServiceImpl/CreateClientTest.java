package com.crm.service.serviceImpl.unit.clientServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.CompanyRepository;
import com.crm.dao.MessageParticipantRepository;
import com.crm.dao.MessageRepository;
import com.crm.entity.Client;
import com.crm.entity.Company;
import com.crm.service.serviceImpl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageParticipantRepository messageParticipantRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldCreateClientWithExistingCompany() {
        // given
        Integer messageId = null;
        Company company = new Company();
        company.setId(1);

        Client client = new Client();
        client.setName("Test Client");

        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(clientRepository.save(client)).thenReturn(client);

        // when
        Client createdClient = clientService.createClient(messageId, company, client);

        // then
        assertThat(createdClient).isNotNull();
        assertThat(createdClient.getCompany()).isEqualTo(company);
        verify(companyRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).save(client);
    }
}
