package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "sent_date")
    private Timestamp sentDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "messages")
    @JsonIgnore
    private List<MessageFolder> messageFolders;


    public Message() {}

    public Message(Integer id, String subject, String body, Timestamp sentDate, Status status) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.sentDate = sentDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<MessageFolder> getMessageFolders() {
        return messageFolders;
    }

    public void setMessageFolders(List<MessageFolder> messageFolders) {
        this.messageFolders = messageFolders;
    }

    public enum Status {
        NEW,
        REPLY,
        FORWARD,
        DELETE,
        SENT,
        DRAFT,
        FOLLOW_UP,
        TRASH
    }
}
