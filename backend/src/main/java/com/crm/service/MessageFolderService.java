package com.crm.service;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDto;
import com.crm.entity.MessageFolder;

import java.util.List;
import java.util.Optional;

public interface MessageFolderService {
    Optional<MessageFolderDto> findById(int parentFolderId);
    List<MessageFolderDto> findAllMessageFolders();
    MessageFolderDto deleteFolder(int folderId);
    List<MessageDTO> deleteAllMessagesFromFolder(int folderId);
    MessageFolderDto createOrUpdateMessageFolder(MessageFolderDto messageFolderDto);
}
