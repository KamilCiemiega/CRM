package com.crm.service.serviceImpl.unit.messageServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.serviceImpl.MessageServiceImpl;
import com.crm.util.MessageServiceTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class SaveMessageTest {
    @Mock
    MessageRepository messageRepository;
    @Mock
    AttachmentRepository attachmentRepository;
    @Mock
    MessageFolderRepository messageFolderRepository;
    @Mock
    MessageRoleRepository messageRoleRepository;
    @Mock
    MessageParticipantRepository messageParticipantRepository;
    @InjectMocks
    MessageServiceImpl underTest;

    MessageFolder messageFolder;
    MessageParticipant messageParticipant;
    MessageRole messageRole;
    Attachment attachment;
    Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        messageFolder = MessageServiceTestDataHelper.createMessageFolder();
        messageParticipant = MessageServiceTestDataHelper.createMessageParticipant();
        messageRole = MessageServiceTestDataHelper.createMessageRole(messageParticipant);
        attachment = MessageServiceTestDataHelper.createAttachment();
        List<MessageRole> roles = new ArrayList<>();
        roles.add(messageRole);
        message = MessageServiceTestDataHelper.createMessage(messageFolder, messageParticipant, roles, attachment);
    }

    @Test
    void shouldSaveMessageWithCorrectDataAndRelations() {
        // given
        when(messageFolderRepository.findById(anyInt())).thenReturn(Optional.of(messageFolder));
        when(messageParticipantRepository.findById(anyInt())).thenReturn(Optional.of(messageParticipant));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message savedMessage = invocation.getArgument(0);
            savedMessage.setId(1);
            return savedMessage;
        });

        // when
        Message savedMessage = underTest.save(message);

        // then
        verify(messageFolderRepository, times(1)).findById(anyInt());
        verify(messageParticipantRepository, times(1)).findById(anyInt());
        verify(messageRepository, times(1)).save(any(Message.class));

        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getId()).isEqualTo(1);

        assertThat(attachment.getMessage()).isEqualTo(savedMessage);
        assertThat(messageFolder.getMessages()).contains(savedMessage);

        assertThat(savedMessage.getMessageRoles().size()).isEqualTo(1);
        assertThat(messageRole.getMessage()).isEqualTo(savedMessage);
    }
}
