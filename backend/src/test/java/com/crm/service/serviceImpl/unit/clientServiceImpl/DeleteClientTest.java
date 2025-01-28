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
public class DeleteClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldDeleteClient() {
        // given
        Integer clientId = 1;
        Client client = new Client();
        client.setId(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        // when
        Client deletedClient = clientService.delete(clientId);

        // then
        assertThat(deletedClient).isNotNull();
        assertThat(deletedClient.getId()).isEqualTo(clientId);
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).delete(client);
    }
}
