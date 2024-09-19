package com.crm.controller.dto;

import com.crm.entity.Message;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Integer id;

    private String subject;

    private String body;

    private Timestamp sentDate;

    @Enumerated(EnumType.STRING)
    private Message.Status status;

    private Long size;
}
