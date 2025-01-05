package com.crm.controller.dto;

import com.crm.entity.Company;
import com.crm.entity.Message;
import com.crm.entity.Reporting;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Integer id;
    private String subject;
    private String body;
    private Timestamp sentDate;
    private Message.Status status;
    private Long size;
    private boolean isUnlinked;
    private Set<AttachmentDTO> attachments = new HashSet<>();
    private Set<MessageFolderDTO> messageFolders = new HashSet<>();
    private Set<MessageRoleDTO> messageRoles = new HashSet<>();
    private Set<ReportingDetailsDTO> reportings = new HashSet<>();
}
