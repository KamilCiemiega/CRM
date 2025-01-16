package com.crm.service;

import com.crm.entity.Message;
import com.crm.enums.MessageSortType;

import java.util.List;

public interface MessageService {
    Message save(Message message);
    List<Message> findAllMessage();
    Message updateMessage(int messageId, Message message);
    Message deleteMessage(int messageId);
    List<Message> getSortedMessages(int folderId, MessageSortType sortType, String orderType);
    Message getMessageById(int messageId);
}
