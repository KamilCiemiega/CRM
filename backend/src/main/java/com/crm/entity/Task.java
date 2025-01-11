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
@Table(name="task")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="topic")
    private String topic;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name="description")
    private String description;

    @CreatedDate
    @Column(name="created_at", updatable = false)
    private Timestamp created_at;

    @LastModifiedDate
    @Column(name="updated_at")
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name = "task_creator_id")
    private User userTaskCreator;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "task_worker_id", referencedColumnName = "id")
    private User userTaskWorker;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Task> subTasks = new ArrayList<>();

    @OneToMany(mappedBy = "taskNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserNotification> userNotifications = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Attachment> attachments = new ArrayList<>();

    public enum TaskStatus {PENDING, IN_PROGRESS, COMPLETED, CANCELED}
}
