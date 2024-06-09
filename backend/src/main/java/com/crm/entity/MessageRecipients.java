package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MessageRecipients")
public class MessageRecipients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RecipientType recipientType;


    public MessageRecipients() {

    }

    public MessageRecipients(Message message, User user, Client client, RecipientType recipientType) {
        this.message = message;
        this.user = user;
        this.client = client;
        this.recipientType  = recipientType;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public enum RecipientType {
        TO, CC
    }


}
