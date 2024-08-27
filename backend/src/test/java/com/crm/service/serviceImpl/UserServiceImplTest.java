package com.crm.service.serviceImpl;

import com.crm.dao.UserRepository;
import com.crm.entity.User;
import com.crm.service.PasswordResetTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    PasswordResetTokenService passwordResetTokenService;
    private UserServiceImpl underTest;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);


    @BeforeEach
    void setUp(){
        underTest = new UserServiceImpl(userRepository, passwordEncoder, passwordResetTokenService);
    }

    @Test
    void save() {
        // given
        User user = new User();
        user.setEmail("testEmail@gmail.com");
        user.setPassword("password123");

        // when
        underTest.save(user);

        // then
        verify(userRepository).save(user);
    }

    @Test
    void changePassword() {
        // given
        User user = new User();
        String newPassword = "plainPassword";
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        // when
        underTest.changePassword(user, newPassword);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        logger.info("User name " + capturedUser);

        // then
        assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void findByEmailAndPassword() {
        //given
        User user = new User();
        String email = "test@gmail.com";
        String plainPassword = "plainPassword";
        String encodedPassword = "$2a$10$DOWOLNIKODHASHINGOWY/WSF";
        user.setEmail(email);
        user.setPassword(encodedPassword);

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(plainPassword, encodedPassword)).thenReturn(true);

        // then
        Optional<User> result = underTest.findByEmailAndPassword(email, plainPassword);
        assertThat(result).isPresent();
        result.ifPresent(u -> {
            assertThat(u.getEmail()).isEqualTo(email);
        });
    }

    @Test
    void findByEmail() {
        // given
        String email = "test@gmail.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        Optional<User> result = underTest.findByEmail(email);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
    }

    @Test
    void createPasswordResetTokenForUser() {
        // given
        User user = new User();
        String passwordResetToken = "tokenReset";

        // when
        passwordResetTokenService.createPasswordResetTokenForUser(user,passwordResetToken);

        // then
        underTest.createPasswordResetTokenForUser(user,passwordResetToken);
    }

    @Test
    void findAllUsers() {
        // given
        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        User user2 = new User();
        user2.setEmail("user2@gmail.com");
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // when
        List<User> result = underTest.findAllUsers();

        // then
        boolean user1Exists = result.stream()
                .anyMatch(user -> user.getEmail().equals(user1.getEmail()));

        assertThat(user1Exists).isTrue();
    }

    @Test
    void findUserByPasswordToken() {
        // given
        String token = "passwordToken";
        User user = new User();
        when(passwordResetTokenService.findUserByPasswordToken(token)).thenReturn(Optional.of(user));

        // when
        Optional<User> result = underTest.findUserByPasswordToken(token);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }
}