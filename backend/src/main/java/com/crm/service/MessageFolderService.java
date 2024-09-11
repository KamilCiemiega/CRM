package com.crm.service;

import com.crm.controller.dto.MessageFolderDto;
import com.crm.entity.MessageFolder;

import java.util.List;
import java.util.Optional;

public interface MessageFolderService {
    MessageFolder save(MessageFolder messageFolder);
    Optional<MessageFolderDto> findById(int parentFolderId);
    List<MessageFolderDto> findAllMessageFolders();
    MessageFolderDto deleteFolder(int folderId);
    MessageFolderDto createOrUpdateMessageFolder(MessageFolderDto messageFolderDto);
}
