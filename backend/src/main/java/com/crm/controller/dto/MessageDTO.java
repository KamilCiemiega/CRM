package com.crm.controller.dto;

import com.crm.entity.Message;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Integer id;
    private String subject;
    private String body;
    private Timestamp sentDate;
    @Enumerated(EnumType.STRING)
    private Message.Status status;
    private Long size;
    private List<AttachmentDTO> attachmentDTOs = new ArrayList<>();
    private int folderId;
    private List<MessageFolderDTO> messageFolders = new ArrayList<>();
}
