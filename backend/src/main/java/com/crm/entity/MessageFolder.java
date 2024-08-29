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

    @Column(name="parentFolder")
    private int parentFolder;

    @Column(name="ownerUserId")
    private int ownerUserId;

    @ManyToOne
    @JoinColumn(name="ownerUserId", nullable=false)
    private User owner;

}
