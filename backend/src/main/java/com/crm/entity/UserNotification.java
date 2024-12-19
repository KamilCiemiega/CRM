package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "reporting_id")
    private User userReportingNotification;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private User userTaskNotification;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum NotificationType {TASK, REPORTING}

}
