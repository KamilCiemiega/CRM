package com.crm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageParticipantDTO {
    private Integer id;
    private Integer messageId;
    private Integer userId;
    private String role;
}
