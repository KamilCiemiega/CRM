package com.crm.dao;

import com.crm.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m JOIN m.messageFolders mf WHERE mf.id = :folderId ORDER BY m.sentDate ASC")
    List<Message> findSortedMessagesByFolder(
            @Param("folderId") Integer folderId
    );
    @Query("SELECT m FROM Message m JOIN m.messageFolders mf WHERE mf.id = :folderId ORDER BY m.size ASC")
    List<Message> findSortedMessagesBySize(
            @Param("folderId") Integer folderId
    );
}

