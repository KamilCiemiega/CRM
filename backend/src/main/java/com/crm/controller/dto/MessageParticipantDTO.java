package com.crm.controller.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageParticipantDTO {
    private Integer id;
    private Integer messageId;
    private Integer userId;
    private String role;
}
