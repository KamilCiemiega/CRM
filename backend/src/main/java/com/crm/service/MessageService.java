package com.crm.service;

import com.crm.entity.Message;
import com.crm.enums.MessageSortType;
import com.crm.controller.dto.MessageDTO;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message save(Message message);
    List<MessageDTO> findAllMessage();
    Optional<MessageDTO> findById(int messageId);
    MessageDTO updateMessage(int messageId, MessageDTO messageDTO);
    MessageDTO deleteMessage(int messageId);
    List<MessageDTO> getSortedMessages(int folderId, MessageSortType sortType, String orderType);
}
