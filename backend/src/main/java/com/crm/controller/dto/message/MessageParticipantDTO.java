package com.crm.controller.dto.message;

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
    private Integer userId;
    private Integer clientId;
    private String type;
}
