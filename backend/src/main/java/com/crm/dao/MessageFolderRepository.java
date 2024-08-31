package com.crm.dao;

import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageFolderRepository extends JpaRepository<MessageFolder, Integer> {
    Optional<MessageFolder> findByNameAndOwner(String name, User owner);
}
