package com.crm.dao;

import com.crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp(){
        User firstUser = new User();
        firstUser.setFirstName("John");
        firstUser.setLastName("Johanson");
        firstUser.setEmail("John@gmail.com");
        underTest.save(firstUser);

        User secondUser = new User();
        secondUser.setFirstName("Mark");
        secondUser.setLastName("Markson");
        secondUser.setEmail("Mark@gmail.com");
        underTest.save(secondUser);
    }

    @Test
    void findByEmail(){
        //given
        String email = underTest.findAll().stream()
                    .findFirst()
                    .map(User::getEmail)
                    .orElseThrow();

        //when
        User findedUser = underTest.findByEmail(email).orElseThrow();

        //then
        assertThat(findedUser).isNotNull();
        assertThat(findedUser.getEmail()).isEqualTo("John@gmail.com");
    }

    @Test
    void findAllByIds(){
        //given
        List<Integer> userIds = underTest.findAll().stream()
                .map(User::getId)
                .toList();
        //then
        List<User> users = underTest.findAllByIds(userIds);
        //when
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }
}
