package com.crm.service.serviceImpl.unit.messageServiceImpl;

import com.crm.dao.*;
import com.crm.enums.MessageSortType;
import com.crm.entity.*;
import com.crm.service.serviceImpl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GetSortedMessageTests {
    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnMessagesSortedBySubjectAsc() {
        // given
        int folderId = 1;
        String orderType = "ASC";
        MessageSortType sortType = MessageSortType.SUBJECT;

        List<Message> messages = new ArrayList<>();

        Message message1 = new Message();
        message1.setId(1);
        message1.setSubject("B - Subject");
        message1.setBody("Body");

        Message message2 = new Message();
        message2.setId(2);
        message2.setSubject("A - Subject");
        message2.setBody("Body");

        messages.add(message1);
        messages.add(message2);

        when(messageRepository.findMessagesByFolderId(eq(folderId), any(Sort.class))).thenReturn(messages);

        // when
        List<Message> result = underTest.getSortedMessages(folderId, sortType, orderType);

        // then
        verify(messageRepository, times(1)).findMessagesByFolderId(eq(folderId), any(Sort.class));
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSubject()).isEqualTo("B - Subject");
    }

    @Test
    void shouldReturnMessagesSortedBySizeDesc() {
        // given
        int folderId = 1;
        String orderType = "DESC";
        MessageSortType sortType = MessageSortType.SIZE;

        List<Message> messages = new ArrayList<>();

        Message smallMessage = new Message();
        smallMessage.setId(1);
        smallMessage.setSubject("Small Message");
        smallMessage.setBody("Body");

        Message largeMessage = new Message();
        largeMessage.setId(2);
        largeMessage.setSubject("Large Message");
        largeMessage.setBody("Body".repeat(100));

        messages.add(largeMessage);
        messages.add(smallMessage);

        when(messageRepository.findMessagesByFolderId(eq(folderId), any(Sort.class))).thenReturn(messages);

        // when
        List<Message> result = underTest.getSortedMessages(folderId, sortType, orderType);

        // then
        verify(messageRepository, times(1)).findMessagesByFolderId(eq(folderId), any(Sort.class));
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getBody().length()).isGreaterThan(result.get(1).getBody().length());
    }

    @Test
    void shouldDefaultToSentDateWhenSortTypeIsInvalid() {
        // given
        int folderId = 1;
        String orderType = "ASC";
        MessageSortType sortType = MessageSortType.DATE;

        List<Message> messages = new ArrayList<>();

        Message olderMessage = new Message();
        olderMessage.setId(1);
        olderMessage.setSubject("Older Message");
        olderMessage.setBody("Body");

        Message newerMessage = new Message();
        newerMessage.setId(2);
        newerMessage.setSubject("Newer Message");
        newerMessage.setBody("Body");

        messages.add(newerMessage);
        messages.add(olderMessage);

        when(messageRepository.findMessagesByFolderId(eq(folderId), any(Sort.class))).thenReturn(messages);

        // when
        List<Message> result = underTest.getSortedMessages(folderId, sortType, orderType);

        // then
        verify(messageRepository, times(1)).findMessagesByFolderId(eq(folderId), any(Sort.class));
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSubject()).isEqualTo("Newer Message");
    }
}
