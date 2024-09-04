package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="messagelocation")
public class MessageLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name="folder_id")
    private MessageFolder messageFolder;

    public MessageLocation() {}

    public MessageLocation(Integer id, Message message, MessageFolder messageFolder) {
        this.id = id;
        this.message = message;
        this.messageFolder = messageFolder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MessageFolder getMessageFolder() {
        return messageFolder;
    }

    public void setMessageFolder(MessageFolder messageFolder) {
        this.messageFolder = messageFolder;
    }


}
