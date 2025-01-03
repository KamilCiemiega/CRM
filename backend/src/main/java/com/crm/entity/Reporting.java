package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reporting")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reporting {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="topic")
    private String topic;

    @Enumerated(EnumType.STRING)
    private ReportingStatus status;

    @Column(name="description")
    private String description;

    @CreatedDate
    @Column(name="created_at", updatable = false)
    private Timestamp created_at;

    @LastModifiedDate
    @Column(name="updated_at")
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name="assigned_user_id")
    private User user;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="reporting_message",
            joinColumns = @JoinColumn(name = "reporting_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "reportingNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserNotification> userNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "reporting", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    public enum ReportingStatus {PENDING, IN_PROGRESS, COMPLETED, CANCELED}

}
