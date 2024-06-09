package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "file_path")
    private String filePath;

    public Attachment() {

    }

    public Attachment(Message message, String filePath) {
        this.message = message;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
