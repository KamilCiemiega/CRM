package com.crm.dao;

import com.crm.entity.PasswordResetToken;
import com.crm.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository underTest;
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        userRepository.save(user);
    }
    @AfterEach
    void tearDown(){
        underTest.deleteAll();;
        userRepository.deleteAll();
    }

    @Test
    void findByToken() {
        // given
        String token = "token";
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        underTest.save(passwordResetToken);

        //when
        PasswordResetToken expectedToken = underTest.findByToken(token);

        // then
        assertThat(expectedToken).isNotNull();
        assertThat(expectedToken.getToken()).isEqualTo(token);
        assertThat(expectedToken.getUser()).isEqualTo(user);

    }
}