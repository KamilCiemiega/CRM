package com.crm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageFolderDto {
    private Integer id;
    private String name;
    private Integer parentFolderId;
    private Integer ownerUserId;
    private UserDto user;
    private int defaultFolder;
}