package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="subject")
    private String subject;

    @Column(name="body")
    private String body;

    @Column(name="sent_date")
    private Timestamp sentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @ManyToMany(mappedBy = "messages")
    private List<Folder> folders;

    @OneToMany(mappedBy = "message")
    private List<MessageRecipients> messageRecipients;

    @OneToOne(mappedBy = "message")
    private Attachment attachment;

    public Message() {

    }

    public Message(User user, Client client, String subject, String body, Timestamp sentDate, StatusType status) {
        this.user = user;
        this.client = client;
        this.subject = subject;
        this.body = body;
        this.sentDate = sentDate;
        this.status  = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSent_date(Timestamp sentDate) {
        this.sentDate = sentDate;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public enum StatusType {
        NEW, REPLY, FORWARD, DELETE, SENT, DRAFT, FOLLOW_UP, TRASH
    }

}
