package com.crm.dao;

import com.crm.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.List;


@DataJpaTest
class MessageRepositoryTest {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MessageRepositoryTest.class);

    @Autowired
    private MessageRepository underTest;
    @Autowired
    private MessageFolderRepository messageFolderRepository;

    @BeforeEach
    void setUp() {
            MessageFolder messageFolder = new MessageFolder();
            messageFolder.setName("testName");
            messageFolder.setFolderType(MessageFolder.FolderType.SYSTEM);
            MessageFolder savedMessageFolder = messageFolderRepository.save(messageFolder);

            Message message = new Message();
            message.setSubject("Test subject");
            message.setBody("Test body");
            message.getMessageFolders().add(savedMessageFolder);
            Message savedMessage = underTest.save(message);
            savedMessageFolder.getMessages().add(savedMessage);
           logger.info("savedMessage: {}", savedMessage);
    }

    @Test
    void findMessagesByFolderId() {
        // given
        Integer folderId = messageFolderRepository.findAll().get(0).getId();
        Sort sort = Sort.by("size");

        // when
        List<Message> messages = underTest.findMessagesByFolderId(folderId, sort);

        // then
        assertThat(messages).isNotNull();
        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getSubject()).isEqualTo("Test subject");
    }

    @Test
    void findAllByIds() {
        // given
        List<Integer> messageIds = underTest.findAll().stream()
                .map(Message::getId)
                .toList();
        logger.info("Client IDs: {}", messageIds);

        // when
        List<Message> messages = underTest.findAllByIds(messageIds);

        // then
        assertThat(messages).isNotNull();
        assertThat(messages.size()).isEqualTo(1);
    }
}
