package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="messagefolders")
public class MessageFolder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @ManyToOne
    @JoinColumn(name="message_id")
    private Messages message;

    @ManyToOne
    @JoinColumn(name="folder_id")
    private Folder folder;
    public MessageFolder() {

    }

    public MessageFolder(Messages message, Folder folder) {
        this.message = message;
        this.folder = folder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages message) {
        this.message = message;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
