package com.crm.configuration;

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

        return modelMapper;
    }


}