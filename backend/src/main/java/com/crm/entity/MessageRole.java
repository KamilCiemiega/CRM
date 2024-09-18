package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="messagerole")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleStatus status;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private MessageParticipant participant;

    public enum RoleStatus {
        TO,
        CC
    }

}
