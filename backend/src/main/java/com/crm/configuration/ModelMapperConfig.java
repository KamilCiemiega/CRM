package com.crm.configuration;

import com.crm.controller.dto.*;
import com.crm.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class ModelMapperConfig {
    private final PropertyMap<Message, MessageDTO> messageToDtoMapping;
    private final PropertyMap<MessageDTO, Message> dtoToMessageMapping;

    public ModelMapperConfig(PropertyMap<Message, MessageDTO> messageToDtoMapping,
                             PropertyMap<MessageDTO, Message> dtoToMessageMapping) {
        this.messageToDtoMapping = messageToDtoMapping;
        this.dtoToMessageMapping = dtoToMessageMapping;
    }
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

        Converter<Timestamp, OffsetDateTime> timestampToOffsetDateTimeConverter = context -> {
            Timestamp source = context.getSource();
            return source == null ? null : OffsetDateTime.ofInstant(source.toInstant(), ZoneOffset.UTC);
        };
        Converter<OffsetDateTime, Timestamp> offsetDateTimeToTimestampConverter = context -> {
            OffsetDateTime source = context.getSource();
            return source == null ? null : Timestamp.from(source.toInstant());
        };

        Converter<Integer, Company> companyConverter = createIdToEntityConverter(Company.class, modelMapper);
        Converter<Integer, Reporting> reportingConverter = createIdToEntityConverter(Reporting.class, modelMapper);
        Converter<Integer, User> userConverter = createIdToEntityConverter(User.class, modelMapper);
        Converter<Integer, Task> taskConverter = createIdToEntityConverter(Task.class, modelMapper);

        modelMapper.addConverter(longToOffsetDateTimeConverter);
        modelMapper.addConverter(timestampToOffsetDateTimeConverter);
        modelMapper.addConverter(offsetDateTimeToTimestampConverter);

        modelMapper.addMappings(messageToDtoMapping);
        modelMapper.addMappings(dtoToMessageMapping);

        configureCompanyMapping(modelMapper, companyConverter);
        configureReportingMapping(modelMapper, companyConverter, reportingConverter, userConverter);
        configureTaskMapping(modelMapper, userConverter, taskConverter, reportingConverter);

        return modelMapper;
    }

    private <D> Converter<Integer, D> createIdToEntityConverter(Class<D> destinationType, ModelMapper modelMapper) {
        return context -> {
            Integer source = context.getSource();
            return source == null ? null : modelMapper.map(source, destinationType);
        };
    }

    private void configureCompanyMapping(ModelMapper modelMapper, Converter<Integer, Company> companyConverter) {
        modelMapper.addMappings(new PropertyMap<CompanyDTO, Company>() {
            @Override
            protected void configure() {
                using(companyConverter).map(source.getId()).setId(null);
                map(source.getClientDTOs()).setClients(null);
            }
        });
    }

    private void configureReportingMapping(ModelMapper modelMapper,
                                           Converter<Integer, Company> companyConverter,
                                           Converter<Integer, Reporting> reportingConverter,
                                           Converter<Integer, User> userConverter) {
        modelMapper.addMappings(new PropertyMap<ReportingDTO, Reporting>() {
            @Override
            protected void configure() {
                using(companyConverter).map(source.getCompanyId()).setCompany(null);
                using(userConverter).map(source.getAssignedUserId()).setUser(null);
                map(source.getUserNotificationDTOs()).setUserNotifications(null);
                map(source.getMessageDTOs()).setMessages(null);
                map(source.getTaskDTOs()).setTasks(null);
            }
        });
    }

    private void configureTaskMapping(ModelMapper modelMapper,
                                      Converter<Integer, User> userConverter,
                                      Converter<Integer, Task> taskConverter,
                                      Converter<Integer, Reporting> reportingConverter) {
        modelMapper.addMappings(new PropertyMap<TaskDTO, Task>() {
            @Override
            protected void configure() {
                using(userConverter).map(source.getUserCreatorId()).setUserTaskCreator(null);
                using(userConverter).map(source.getUserWorkerId()).setUserTaskWorker(null);
                using(taskConverter).map(source.getParentTaskId()).setParentTask(null);
                map(source.getSubTasksDTOs()).setSubTasks(null);
                using(reportingConverter).map(source.getReportingId()).setReporting(null);
                map(source.getUserNotificationDTOs()).setUserNotifications(null);
            }
        });
    }
}