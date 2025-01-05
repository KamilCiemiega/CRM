package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
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

    @CreatedDate
    @Column(name = "sent_date")
    private Timestamp sentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "size")
    private Long size;

    @JsonIgnore
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageRole> messageRoles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "messages")
    private Set<MessageFolder> messageFolders = new HashSet<>();

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attachment> attachments = new HashSet<>();

    @Column(name = "is_unlinked")
    private boolean isUnlinked;

    @JsonIgnore
    @ManyToMany(mappedBy = "messages")
    private Set<Reporting> reportings = new HashSet<>();

    @PrePersist
    @PreUpdate
    public void calculateSize() {
        this.size = (long) (body != null ? body.getBytes().length : 0);
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
