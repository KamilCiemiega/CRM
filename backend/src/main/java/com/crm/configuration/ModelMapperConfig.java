package com.crm.configuration;

import com.crm.controller.dto.AttachmentDTO;
import com.crm.controller.dto.task.SimpleTaskDTO;
import com.crm.controller.dto.task.TaskDTO;
import com.crm.dao.UserRepository;
import com.crm.entity.Attachment;
import com.crm.entity.Task;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ModelMapperConfig {
    public ModelMapperConfig() {}

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Enum<?>, String> enumToStringConverter = new Converter<Enum<?>, String>() {
            @Override
            public String convert(MappingContext<Enum<?>, String> context) {
                return context.getSource() != null ? context.getSource().name() : null;
            }
        };
        modelMapper.typeMap(Task.class, TaskDTO.class).addMappings(mapper -> {
            mapper.map(src -> {
                Task parent = src.getParentTask();
                return parent != null ? new SimpleTaskDTO(parent.getId()) : null;
            }, TaskDTO::setParentTask);

            mapper.map(Task::getId, TaskDTO::setId);
        });

        modelMapper.typeMap(AttachmentDTO.class, Attachment.class)
                .addMappings(mapper -> mapper.skip(Attachment::setTicket));

        modelMapper.addConverter(enumToStringConverter);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }
}