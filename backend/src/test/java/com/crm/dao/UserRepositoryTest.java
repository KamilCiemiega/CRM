package com.crm.dao;

import com.crm.entity.Role;
import com.crm.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    private UserRepository underTest;
    @Autowired
    private  RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1);
        role.setName("admin");
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        // given
        String email = "testEmail@gmail.com";
        Role role = roleRepository.findById(1).orElseThrow();

        // Create and save a User with the existing Role
        User user = new User();
        underTest.save(user);

        // when
        Optional<User> expectedUser = underTest.findByEmail(email);

        // then
        assertThat(expectedUser).isPresent();
        expectedUser.ifPresent(u -> {
            logger.info("Found user: {}", u);
            assertThat(u.getEmail()).isEqualTo(email);
            assertThat(u.getRole().getName()).isEqualTo("admin");
        });
    }
}