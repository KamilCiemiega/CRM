package com.crm.service;

import com.crm.controller.dto.MessageDTO;
import com.crm.controller.dto.MessageFolderDTO;
import com.crm.entity.Message;
import com.crm.entity.MessageFolder;

import java.util.List;
import java.util.Optional;

public interface MessageFolderService {
    MessageFolder save(MessageFolder MessageFolder);
    List<MessageFolder> findAllMessageFolders();
    MessageFolder deleteFolder(int folderId);
    List<Message> deleteAllMessagesFromFolder(int folderId);
    MessageFolder updateMessageFolder(int folderId, MessageFolder messageFolder);
}
