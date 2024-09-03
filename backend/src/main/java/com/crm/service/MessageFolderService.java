package com.crm.service;

import com.crm.entity.MessageFolder;
import com.crm.entity.User;

import java.util.Optional;

public interface MessageFolderService {
    MessageFolder save(MessageFolder messageFolder);
    Optional<MessageFolder> findByNameAndUser(String messageFolderName, User user);
}
