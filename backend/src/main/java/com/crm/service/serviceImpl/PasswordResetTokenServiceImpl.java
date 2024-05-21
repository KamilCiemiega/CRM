package com.crm.service.serviceImpl;

import com.crm.dao.PasswordResetTokenRepository;
import com.crm.entity.PasswordResetToken;
import com.crm.entity.User;
import com.crm.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }
    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid verification token";
        }
        User user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }
    @Override
    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(passwordResetToken);
        if (token != null) {
            return Optional.ofNullable(token.getUser());
        }
        return Optional.empty();
    }
    @Override
    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}
