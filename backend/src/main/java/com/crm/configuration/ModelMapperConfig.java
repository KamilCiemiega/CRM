package com.crm.configuration;

import com.crm.entity.Reporting;
import com.crm.entity.Task;
import com.crm.entity.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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

        modelMapper.addConverter(longToOffsetDateTimeConverter());
        modelMapper.addConverter(timestampToOffsetDateTimeConverter());
        modelMapper.addConverter(offsetDateTimeToTimestampConverter());

<<<<<<< HEAD
=======
        Converter<Enum<?>, String> enumToStringConverter = new Converter<Enum<?>, String>() {
            @Override
            public String convert(MappingContext<Enum<?>, String> context) {
                return context.getSource() != null ? context.getSource().name() : null;
            }
        };

        modelMapper.addConverter(enumToStringConverter);
        modelMapper.addConverter(longToOffsetDateTimeConverter);

>>>>>>> 9421af8 (mostly working on TicketService save method)
        return modelMapper;
    }

    @Bean
    public Converter<Long, OffsetDateTime> longToOffsetDateTimeConverter() {
        return context -> {
            Long source = context.getSource();
            return source == null ? null : OffsetDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneOffset.UTC);
        };
    }

    @Bean
    public Converter<Timestamp, OffsetDateTime> timestampToOffsetDateTimeConverter() {
        return context -> {
            Timestamp source = context.getSource();
            return source == null ? null : OffsetDateTime.ofInstant(source.toInstant(), ZoneOffset.UTC);
        };
    }

    @Bean
    public Converter<OffsetDateTime, Timestamp> offsetDateTimeToTimestampConverter() {
        return context -> {
            OffsetDateTime source = context.getSource();
            return source == null ? null : Timestamp.from(source.toInstant());
        };
    }
}