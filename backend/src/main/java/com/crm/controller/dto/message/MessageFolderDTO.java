package com.crm.controller.dto.message;

import com.crm.controller.dto.user.UserDTO;
import com.crm.entity.MessageFolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageFolderDTO {
    private Integer id;
    private String name;
    private MessageFolderDTO parentFolder;
    private List<MessageFolderDTO> subFolders = new ArrayList<>();
    private List<MessageDTO> messages = new ArrayList<>();
    private UserDTO user;
    private MessageFolder.FolderType folderType;
}