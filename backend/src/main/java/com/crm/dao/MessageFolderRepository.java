package com.crm.dao;

import com.crm.entity.MessageFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFolderRepository extends JpaRepository<MessageFolder, Integer> {

}
