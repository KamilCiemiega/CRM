package com.crm.configuration;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Long, OffsetDateTime> longToOffsetDateTimeConverter = new Converter<Long, OffsetDateTime>() {
            @Override
            public OffsetDateTime convert(MappingContext<Long, OffsetDateTime> context) {
                Long source = context.getSource();
                return source == null ? null : OffsetDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneOffset.UTC);
            }
        };

        modelMapper.addConverter(longToOffsetDateTimeConverter);
        return modelMapper;
    }
}