package com.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messagelocation")
@Data
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
