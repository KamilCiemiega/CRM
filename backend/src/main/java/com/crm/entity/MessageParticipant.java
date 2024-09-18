package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ParticipantType type;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<MessageRole> messageRoles;

    public enum ParticipantType {
        CUSTOMER,
        USER
    }

}
