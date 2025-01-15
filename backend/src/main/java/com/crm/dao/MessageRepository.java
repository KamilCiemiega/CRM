package com.crm.dao;

import com.crm.entity.Message;
import com.crm.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m JOIN m.messageFolders mf WHERE mf.id = :folderId")
    List<Message> findMessagesByFolderId(@Param("folderId") Integer folderId, Sort sort);

    @Query("SELECT m FROM Message m JOIN m.messageRoles mr JOIN mr.participant p WHERE p.client.id IN :clientIds")
    List<Message> findMessagesByClientIds(@Param("clientIds") List<Integer> clientIds);

    @Query("SELECT m FROM Message m WHERE m.id IN :ids")
    List<Message> findAllByIds(@Param("ids") List<Integer> ids);
}

