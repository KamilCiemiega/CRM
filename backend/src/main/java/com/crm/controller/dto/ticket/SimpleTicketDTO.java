package com.crm.controller.dto.ticket;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTicketDTO {
    private Integer id;
    private String topic;
    private String status;
    private String type;
    private Timestamp created_at;
    private Timestamp updated_at;
}
