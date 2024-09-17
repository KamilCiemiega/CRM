package com.crm.service;

import com.crm.Enum.MessageSortType;
import com.crm.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message save(Message message);
    List<Message> findAllMessage();
    Optional<Message> findById(int messageId);
    Message createOrUpdateMessage (Message message);
    Message deleteMessage(int messageId);
    List<Message> getSortedMessages(int folderId, MessageSortType sortType, String orderType);
}
