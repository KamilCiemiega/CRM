package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "messagelocation")
public class MessageLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "folder_id")
    private Integer folderId;

    @Column(name = "message_id")
    private Integer messageId;

    public MessageLocation() {}

    public MessageLocation(Integer folderId, Integer messageId) {
        this.folderId = folderId;
        this.messageId = messageId;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
