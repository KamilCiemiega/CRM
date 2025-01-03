package com.crm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailsDTO extends TaskDTO {
    private Integer id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
