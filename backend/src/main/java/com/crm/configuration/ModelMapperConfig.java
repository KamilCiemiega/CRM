package com.crm.configuration;

import com.crm.controller.dto.TaskDTO;
import com.crm.dao.UserRepository;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.exception.NoSuchEntityException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class ModelMapperConfig {

    private final UserRepository userRepository;

    public ModelMapperConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Enum<?>, String> enumToStringConverter = new Converter<Enum<?>, String>() {
            @Override
            public String convert(MappingContext<Enum<?>, String> context) {
                return context.getSource() != null ? context.getSource().name() : null;
            }
        };

//        modelMapper.typeMap(TaskDTO.class, Task.class).addMappings(mapper -> {
//            mapper.map(TaskDTO::getTaskCreatorId, (task, id) -> {
//                User userCreator = userRepository.findById((Integer) id)
//                        .orElseThrow(() -> new NoSuchEntityException("User not found for ID: " + id));
//                task.setUserTaskCreator(userCreator);
//            });
//            mapper.map(TaskDTO::getTaskWorkerId, (task, id) -> {
//                User userWorker = userRepository.findById((Integer)  id)
//                        .orElseThrow(() -> new NoSuchEntityException("User not found for ID: " + id));
//                task.setUserTaskWorker(userWorker);
//            });
//        });

//        modelMapper.typeMap(Task.class, TaskDTO.class).addMappings(mapper -> {
//            mapper.map(task -> task.getUserTaskCreator().getId(), TaskDTO::setTaskCreatorId);
//            mapper.map(task -> task.getUserTaskWorker().getId(), TaskDTO::setTaskWorkerId);
//        });

        modelMapper.addConverter(enumToStringConverter);

        return modelMapper;
    }


}