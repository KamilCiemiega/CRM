package com.crm.configuration;

import com.crm.controller.dto.UserDto;
import com.crm.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<UserDto, User>() {
            @Override
            protected void configure() {
                map().setFirstName(source.getFirstName());
                map().setLastName(source.getLastName());
                map().setEmail(source.getEmail());
                map().setRole(source.getRole());
                map().setId(source.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map().setFirstName(source.getFirstName());
                map().setLastName(source.getLastName());
                map().setEmail(source.getEmail());
                map().setRole(source.getRole());
                map().setId(source.getId());
            }
        });

        return modelMapper;
    }

}

