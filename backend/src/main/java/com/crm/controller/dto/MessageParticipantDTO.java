package com.crm.controller.dto;

import com.crm.entity.MessageParticipant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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
