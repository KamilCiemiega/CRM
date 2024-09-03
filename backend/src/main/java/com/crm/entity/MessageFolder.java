package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="messagefolder")
public class MessageFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentFolder", nullable = true)
    private MessageFolder parentFolder;

    @ManyToOne
    @JoinColumn(name = "ownerUserId")
    private User user;

    public MessageFolder() {
    }

    public MessageFolder(Integer id, String name, MessageFolder parentFolder, User user) {
        this.id = id;
        this.name = name;
        this.parentFolder = parentFolder;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageFolder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(MessageFolder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}