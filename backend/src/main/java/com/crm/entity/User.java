package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="`user`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    @ManyToMany(mappedBy = "users")
    private List<Client> clients;

    @OneToMany(mappedBy = "user")
    private List<MessageFolder> messageFolders;
}
