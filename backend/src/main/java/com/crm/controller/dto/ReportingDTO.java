package com.crm.controller.dto;

import com.crm.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportingDTO {
    private String topic;
    private String status;
    private String description;
    private Integer companyId;
    private Integer assignedUserId;
    private List<Message> messages = new ArrayList<>();
}
