package com.crm.configuration.mapping;

import com.crm.controller.dto.UserNotificationDTO;
import com.crm.entity.Reporting;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.entity.UserNotification;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserNotificationMappingConfig {

    private final Converter<Integer, Reporting> reportingConverter;
    private final Converter<Integer, Task> taskConverter;
    private final Converter<Integer, User> userConverter;

    public UserNotificationMappingConfig(Converter<Integer, Reporting> reportingConverter,
                                         Converter<Integer, Task> taskConverter,
                                         Converter<Integer, User> userConverter) {
        this.reportingConverter = reportingConverter;
        this.taskConverter = taskConverter;
        this.userConverter = userConverter;
    }

    @Bean
    public PropertyMap<UserNotificationDTO, UserNotification> dtoToEntityMapping() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(reportingConverter).map(source.getReportingId()).setReportingNotification(null);
                using(taskConverter).map(source.getTaskId()).setTaskNotification(null);
                using(userConverter).map(source.getUserId()).setUser(null);
            }
        };
    }

    @Bean
    public PropertyMap<UserNotification, UserNotificationDTO> entityToDtoMapping() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map(source.getReportingNotification().getId()).setReportingId(null);
                map(source.getTaskNotification().getId()).setTaskId(null);
                map(source.getUser().getId()).setUserId(null);
            }
        };
    }
}