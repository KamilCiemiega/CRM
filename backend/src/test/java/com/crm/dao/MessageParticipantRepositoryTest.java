package com.crm.dao;

import com.crm.entity.Client;
import com.crm.entity.MessageParticipant;
import com.crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class MessageParticipantRepositoryTest {
    @Autowired
    private MessageParticipantRepository underTest;
    @Autowired UserRepository userRepository;
    @Autowired ClientRepository clientRepository;

    private User savedUser;
    private Client savedClient;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setFirstName("John");
        user.setEmail("john@gmail.com");
        savedUser = userRepository.save(user);

        MessageParticipant userParticipant = new MessageParticipant();
        userParticipant.setType(MessageParticipant.ParticipantType.USER);
        userParticipant.setUser(savedUser);
        underTest.save(userParticipant);

        Client client = new Client();
        client.setName("Client A");
        client.setEmail("clienta@gmail.com");
        savedClient = clientRepository.save(client);

        MessageParticipant clientParticipant = new MessageParticipant();
        clientParticipant.setType(MessageParticipant.ParticipantType.CLIENT);
        clientParticipant.setClient(savedClient);
        underTest.save(clientParticipant);
    }

    @Test
    void findByUserId_shouldReturnParticipant() {
        // given
        Integer userId = savedUser.getId();

        // when
        MessageParticipant result = underTest.findByUserId(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUser().getId()).isEqualTo(userId);
        assertThat(result.getType()).isEqualTo(MessageParticipant.ParticipantType.USER);
    }

    @Test
    void findByClientId_shouldReturnParticipant() {
        // given
        Integer clientId = savedClient.getId();

        // when
        MessageParticipant result = underTest.findByClientId(clientId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getClient().getId()).isEqualTo(clientId);
        assertThat(result.getType()).isEqualTo(MessageParticipant.ParticipantType.CLIENT);
    }
}
