package com.crm.service.serviceImpl.unit.clientServiceImpl;

import com.crm.dao.ClientRepository;
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
class FindByIdTests{

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldReturnClientWhenFound() {
        // given
        int clientId = 1;
        Client client = new Client();
        client.setId(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // when
        Optional<Client> result = clientService.findById(clientId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(clientId);
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void shouldReturnEmptyWhenClientNotFound() {
        // given
        int clientId = 1;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // when
        Optional<Client> result = clientService.findById(clientId);

        // then
        assertThat(result).isEmpty();
        verify(clientRepository, times(1)).findById(clientId);
    }
}
