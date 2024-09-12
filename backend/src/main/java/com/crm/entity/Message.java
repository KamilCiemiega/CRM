package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="message")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
