package com.crm.controller.dto.message;

import com.crm.entity.MessageFolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubFolderDTO {
    private Integer id;
    private String name;
    private Integer parentFolderId;
    private MessageFolder.FolderType folderType;
}
