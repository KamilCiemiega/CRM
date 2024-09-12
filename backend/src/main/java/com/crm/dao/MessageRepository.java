package com.crm.dao;

import com.crm.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m JOIN m.messageFolders mf WHERE mf.id = :folderId AND m.sentDate BETWEEN :startDate AND :endDate")
    List<Message> findMessagesByFolderIdAndDateRange(
            @Param("folderId") Integer folderId,
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate
    );
}

