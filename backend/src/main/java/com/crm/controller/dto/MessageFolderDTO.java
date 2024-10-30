package com.crm.controller.dto;

import com.crm.entity.MessageFolder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageFolderDTO {
    private Integer id;
    private String name;
    private MessageFolder parentFolder;
    private UserDTO user;
    @Enumerated(EnumType.STRING)
    private MessageFolder.FolderType folderType;
}