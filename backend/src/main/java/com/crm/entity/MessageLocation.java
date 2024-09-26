package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messagelocation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "folder_id")
    private Integer folderId;
    @Column(name = "message_id")
    private Integer messageId;
}
