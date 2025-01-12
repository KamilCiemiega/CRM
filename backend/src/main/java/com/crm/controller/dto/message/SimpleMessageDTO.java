package com.crm.controller.dto.message;

import com.crm.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMessageDTO {
    private Integer id;
    private String subject;
    private String body;
    private Timestamp sentDate;
    private String status;
}
