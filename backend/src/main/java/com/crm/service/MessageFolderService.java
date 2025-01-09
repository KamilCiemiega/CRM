package com.crm.service;

import com.crm.entity.Message;
import com.crm.entity.MessageFolder;

import java.util.List;

public interface MessageFolderService {
    MessageFolder save(MessageFolder MessageFolder);
    List<MessageFolder> findAllMessageFolders();
    MessageFolder deleteFolder(int folderId);
    List<Message> deleteAllMessagesFromFolder(int folderId);
    MessageFolder updateMessageFolder(int folderId, MessageFolder messageFolder);
    Message deleteMessageFromFolder(int folderId, int messageId);
}
