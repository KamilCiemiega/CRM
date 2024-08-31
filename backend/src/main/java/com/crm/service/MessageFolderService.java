package com.crm.service;

import com.crm.entity.MessageFolder;
import com.crm.entity.User;

import java.util.Optional;

public interface MessageFolderService {
    MessageFolder save(MessageFolder messageFolder);
    Optional<MessageFolder> findByNameAndOwner(String messageFolderName, User owner);
}
