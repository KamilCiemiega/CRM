package com.crm.dao;

import com.crm.entity.Message;
import com.crm.entity.MessageFolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository underTest;
    @Autowired
    private MessageFolderRepository messageFolderRepository;

    @BeforeEach
    void setUp() {
        MessageFolder messageFolder = new MessageFolder();
        messageFolder.setName("INBOX");
        messageFolder.setFolderType(MessageFolder.FolderType.SYSTEM);
        messageFolderRepository.save(messageFolder);

        Message message = new Message();
        String dateString = "2024-02-11T10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);

        message.setSubject("Test subject");
        message.setBody("Test body");
        message.setSentDate(Timestamp.valueOf(localDateTime));
        message.getMessageFolders().add(messageFolder);
        messageFolder.getMessages().add(message);

        messageFolderRepository.save(messageFolder);
        underTest.save(message);
    }


    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        messageFolderRepository.deleteAll();
    }

    @Test
    void findMessagesByFolderId() {
        //given
        Integer folderId = messageFolderRepository.findAll().get(0).getId();
        Sort sort = Sort.by("size");

        // Log current database state
        List<Message> allMessages = underTest.findAll();
        List<MessageFolder> allFolders = messageFolderRepository.findAll();
        System.out.println("All Messages: " + allMessages);
        System.out.println("All Folders: " + allFolders);

        //when
        List<Message> expectedMessages = underTest.findMessagesByFolderId(folderId, sort);

        //then
       assertThat(expectedMessages).isNotNull();
       assertThat(expectedMessages.stream().anyMatch(m -> m.getSubject().equals("Test subject")))
                .isTrue();
    }
}
