package com.crm.service;

import com.crm.dao.UserRepository;
import com.crm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public User findById(int theId) {
        Optional<User> userOptional = userRepository.findById(theId);
        return userOptional.orElse(null);
    }
}
