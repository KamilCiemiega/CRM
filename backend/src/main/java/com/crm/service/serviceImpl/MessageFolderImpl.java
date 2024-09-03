package com.crm.service.serviceImpl;

import com.crm.dao.MessageFolderRepository;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.service.MessageFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageFolderImpl implements MessageFolderService {

    private final MessageFolderRepository messageFolderRepository;

    @Autowired
    public MessageFolderImpl(MessageFolderRepository messageFolderRepository) {
        this.messageFolderRepository = messageFolderRepository;
    }

    @Override
    public MessageFolder save(MessageFolder messageFolder) {
         return messageFolderRepository.save(messageFolder);
    }

    @Override
    public List<MessageFolder> findAllMessageFolders() {
        return messageFolderRepository.findAll();
    }

    @Override
    public Optional<MessageFolder> findByNameAndUser(String messageFolderName, User user) {
        return messageFolderRepository.findByNameAndUser(messageFolderName, user);
    }

    @Override
    public Optional<MessageFolder> findById(int parentFolderId) {
        return messageFolderRepository.findById(parentFolderId);
    }

}
