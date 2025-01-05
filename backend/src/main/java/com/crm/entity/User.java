package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="`user`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(Integer userId){
        this.id = userId;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MessageFolder> messageFolders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reporting> reportings = new ArrayList<>();

    @OneToOne(mappedBy = "userTaskCreator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Task taskCreator;

    @OneToOne(mappedBy = "userTaskWorker", cascade = CascadeType.ALL, orphanRemoval = true)
    private Task taskWorker;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserNotification> userNotification = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MessageParticipant> messageParticipants = new ArrayList<>();
}
