package com.crm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailsDTO extends TaskDTO {
    private Integer id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
