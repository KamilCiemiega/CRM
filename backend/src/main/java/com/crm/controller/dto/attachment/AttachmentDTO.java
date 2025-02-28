package com.crm.controller.dto.attachment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
    @NotNull
    private String type;
    @NotNull
    private String filePath;
    private Integer messageId;
    private Integer ticketId;
    private Integer taskId;
}