package com.crm.service.serviceImpl;

import com.crm.dao.ClientRepository;
import com.crm.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    private ClientServiceImpl underTest;

    @BeforeEach
    void setUp(){
        underTest = new ClientServiceImpl(clientRepository);
    }

    @Test
    void save() {
        // given
        Client client = new Client();
        client.setEmail("testEmail@gmail.com");

        // when
        underTest.save(client);

        // then
        verify(clientRepository).save(client);
    }

    @Test
    void findAllClient() {
        // given
        Client client1 = new Client();
        client1.setEmail("client1@gmail.com");
        Client client2 = new Client();
        client2.setEmail("client2@gmail.com");
        List<Client> clients = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clients);

        // when
        List<Client> result = underTest.findAllClients();

        // then
        boolean client1Exists = result.stream()
                .anyMatch(client -> client.getEmail().equals(client1.getEmail()));

        assertThat(client1Exists).isTrue();
    }
}