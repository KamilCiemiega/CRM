package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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

    @Column(name = "size")
    private Long size;

    @ManyToMany(mappedBy = "messages", cascade = CascadeType.ALL)// https://www.geeksforgeeks.org/jpa-cascading-operations/
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

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @PrePersist
    @PreUpdate
    public void calculateSize() {
        this.size = (long) (body != null ? body.getBytes().length : 0);
    }
}
