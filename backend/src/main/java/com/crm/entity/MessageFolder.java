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
@Table(
        name = "messagefolder",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_user_id"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private MessageFolder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MessageFolder> subFolders;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "messagelocation",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User user;

    @Column(name = "folder_type")
    @Enumerated(EnumType.STRING)
    private  FolderType folderType;

    public enum FolderType {SYSTEM, USER}
}