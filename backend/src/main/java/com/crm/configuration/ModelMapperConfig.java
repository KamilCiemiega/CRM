package com.crm.configuration;

import com.crm.controller.dto.ReportingDTO;
import com.crm.controller.dto.TaskDTO;
import com.crm.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

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

        modelMapper.addMappings(new PropertyMap<ReportingDTO, Reporting>(){
           @Override
           protected void configure() {

               map().setCompany(mapIdToEntity(source.getCompanyId(), Company.class, modelMapper));
               map().setUser(mapIdToEntity(source.getAssignedUserId(), User.class, modelMapper));
               map().setUserNotifications(mapDtoListToEntity(source.getUserNotificationDTOs(), UserNotification.class, modelMapper));
               map().setMessages(mapDtoListToEntity(source.getMessageDTOs(), Message.class,modelMapper));
               map().setTasks(mapDtoListToEntity(source.getTaskDTOs(), Task.class, modelMapper));
           }

        });

        return modelMapper;
    }

    private <T> T mapIdToEntity(Integer id, Class<T> tClass, ModelMapper modelMapper) {
        return id != null ? modelMapper.map(id, tClass) : null;
    }

    public <T, S> List<T> mapDtoListToEntity(List<S> dtoList, Class<T> tClass, ModelMapper modelMapper) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, tClass))
                .toList();
    }
}