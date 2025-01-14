package com.crm.configuration;

import com.crm.controller.dto.AttachmentDTO;
import com.crm.dao.UserRepository;
import com.crm.entity.Attachment;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
        modelMapper.addConverter(enumToStringConverter);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(AttachmentDTO.class, Attachment.class)
                .addMappings(mapper -> mapper.skip(Attachment::setTicket));
        return modelMapper;
    }


}