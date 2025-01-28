package com.crm.service.serviceImpl.unit.clientServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.entity.Client;
import com.crm.service.serviceImpl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAllClientsTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldReturnAllClients() {
        // given
        Client client1 = new Client();
        client1.setId(1);

        Client client2 = new Client();
        client2.setId(2);

        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));

        // when
        List<Client> result = clientService.findAllClients();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).contains(client1, client2);
        verify(clientRepository, times(1)).findAll();
    }
}
