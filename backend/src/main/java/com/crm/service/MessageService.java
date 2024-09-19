package com.crm.service;

import com.crm.Enum.MessageSortType;
import com.crm.controller.dto.MessageDTO;
import com.crm.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    MessageDTO save(Message message);
    List<MessageDTO> findAllMessage();
    Optional<MessageDTO> findById(int messageId);
    MessageDTO CreateOrUpdateExistingMessage(MessageDTO messageDTO);
    MessageDTO deleteMessage(int messageId);
    List<MessageDTO> getSortedMessages(int folderId, MessageSortType sortType, String orderType);
}
