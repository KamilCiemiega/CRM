package com.crm.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="folders")
public class Folder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "messagefolders",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private List<MessageFolder> messageFolders;

    public Folder() {}

    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}