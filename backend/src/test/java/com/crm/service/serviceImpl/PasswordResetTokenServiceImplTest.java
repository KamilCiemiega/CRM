package com.crm.service.serviceImpl;

import com.crm.dao.PasswordResetTokenRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.PasswordResetToken;
import com.crm.entity.User;
import com.crm.service.PasswordResetTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordResetTokenService passwordResetTokenService;
    private PasswordResetTokenServiceImpl underTest;

    @BeforeEach
    void setUp(){
        underTest = new PasswordResetTokenServiceImpl(passwordResetTokenRepository);
    }

    // Helping classes
    private PasswordResetToken createPasswordResetToken(String token, User user, Date expirationTime) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpirationTime(expirationTime);
        return passwordResetToken;
    }

    private User createUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        return user;
    }

    @Test
    void createPasswordResetTokenForUser() {
        // given
        User user = new User();
        user.setEmail("test@gmail.com");
        userRepository.save(user);
        String passwordToken = "tokenPassword";
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken,user);

        // when
        underTest.createPasswordResetTokenForUser(user, passwordToken);

        ArgumentCaptor<PasswordResetToken> tokenArgumentCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);
        verify(passwordResetTokenRepository).save(tokenArgumentCaptor.capture());

        PasswordResetToken capturedToken = tokenArgumentCaptor.getValue();

        // then
        assertThat(capturedToken).isNotNull();
        assertThat(capturedToken.getToken()).isEqualTo(passwordToken);
        assertThat(capturedToken.getUser()).isEqualTo(user);
    }

    @Test
    void validatePasswordResetToken() {
        // given
        String token = "resetToken";
        User user = createUser();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, calendar.getTime());

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        // when
        String result = underTest.validatePasswordResetToken(token);

        // then
        assertThat(result).isEqualTo("Link already expired, resend link");
    }

    @Test
    void validatePasswordResetToken_whenTokenIsNull() {
        // given
        String token = "invalidToken";
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(null);

        // when
        String result = underTest.validatePasswordResetToken(token);

        // then
        assertThat(result).isEqualTo("Invalid verification token");
    }

    @Test
    void findUserByPasswordToken() {
        // given
        String token = "passwordResetToken";
        User user = createUser();
        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, Calendar.getInstance().getTime());

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        // when
        Optional<User> result = underTest.findUserByPasswordToken(token);

        // then
        assertThat(result).isPresent();
        result.ifPresent(u -> {
            assertThat(u.getEmail()).isEqualTo("test@gmail.com");
        });
    }

    @Test
    void findPasswordResetToken() {
        // given
        String token = "token";
        User user = new User();
        user.setEmail("test@gmail.com");

        PasswordResetToken expectedPasswordResetToken = new PasswordResetToken(token, user);

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(expectedPasswordResetToken);

        // when
        PasswordResetToken result = underTest.findPasswordResetToken(token);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo(expectedPasswordResetToken.getToken());
        assertThat(result.getUser()).isEqualTo(expectedPasswordResetToken.getUser());
    }
}