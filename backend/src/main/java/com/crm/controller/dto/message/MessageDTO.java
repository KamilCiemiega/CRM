package com.crm.controller.dto.message;

import com.crm.controller.dto.attachment.AttachmentDTO;
import com.crm.entity.Message;
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
    private Message.Status status;
    private Long size;
    private boolean isUnlinked;
    private List<AttachmentDTO> attachments = new ArrayList<>();
    private List<MessageFolderDTO> messageFolders = new ArrayList<>();
    private List<MessageRoleDTO> messageRoles = new ArrayList<>();
}
