package com.crm.service;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDTO;

import java.util.List;
import java.util.Optional;

public interface MessageFolderService {
    MessageFolderDTO save(MessageFolderDTO MessageFolderDTO);
    Optional<MessageFolderDTO> findById(int parentFolderId);
    List<MessageFolderDTO> findAllMessageFolders();
    MessageFolderDTO deleteFolder(int folderId);
    List<MessageDTO> deleteAllMessagesFromFolder(int folderId);
    MessageFolderDTO updateMessageFolder(int folderId, MessageFolderDTO messageFolderDTO);
}
