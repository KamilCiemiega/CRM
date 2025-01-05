package com.crm.controller.dto;

import com.crm.entity.Attachment;
import com.crm.entity.Reporting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
    private Integer id;
    private Attachment.Type type;
    private String filePath;
    private ReportingDetailsDTO reporting;
    private TaskDetailsDTO task;
}
