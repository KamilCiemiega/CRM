package com.crm.configuration.mapping;

import com.crm.controller.dto.MessageDTO;
import com.crm.entity.Message;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
    public class MessageMappingConfig {
        @Bean
        public PropertyMap<Message, MessageDTO> messageToDtoMapping() {
            return new PropertyMap<>() {
                @Override
                protected void configure() {
                    map(source.getAttachments()).setAttachmentDTOs(null);
                    map(source.getMessageFolders()).setMessageFolderDTOs(null);
                    map(source.getMessageRoles()).setMessageRoleDTOs(null);
                    map(source.getReportings()).setReportingDTOs(null);
                }
            };
        }

        @Bean
        public PropertyMap<MessageDTO, Message> dtoToMessageMapping() {
            return new PropertyMap<>() {
                @Override
                protected void configure() {
                    map(source.getAttachmentDTOs()).setAttachments(null);
                    map(source.getMessageFolderDTOs()).setMessageFolders(null);
                    map(source.getMessageRoleDTOs()).setMessageRoles(null);
                    map(source.getReportingDTOs()).setReportings(null);
                }
            };
        }
    }
