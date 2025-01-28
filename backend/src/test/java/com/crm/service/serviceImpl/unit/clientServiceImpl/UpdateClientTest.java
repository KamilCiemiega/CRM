package com.crm.service.serviceImpl.unit.clientServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.CompanyRepository;
import com.crm.entity.Client;
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
class UpdateClientTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldUpdateClient() {
        // given
        Integer clientId = 1;
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setEmail("old@test.com");

        Client updateData = new Client();
        updateData.setEmail("new@test.com");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(existingClient);

        // when
        Client updatedClient = clientService.updateClient(clientId, updateData);

        // then
        assertThat(updatedClient).isNotNull();
        assertThat(updatedClient.getEmail()).isEqualTo("new@test.com");
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(existingClient);
    }
}