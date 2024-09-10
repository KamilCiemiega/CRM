package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "messagefolder",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_user_id"})}
)
public class MessageFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    @JsonBackReference
    private MessageFolder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MessageFolder> subFolders;

    @ManyToMany
    @JoinTable(
            name = "messageLocation",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    @JsonIgnore
    private List<Message> messages;


    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User user;

    public MessageFolder() {}

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

    public List<MessageFolder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<MessageFolder> subFolders) {
        this.subFolders = subFolders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}