package com.crm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="messageFolder")
public class MessageFolder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="parentFolder", nullable = true)
    private MessageFolder parentFolder;

    @Column(name="ownerUserId")
    private int ownerUserId;

    @ManyToOne
    @JoinColumn(name="ownerUserId",insertable = false, updatable = false)
    private User user;

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

    public MessageFolder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(MessageFolder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
