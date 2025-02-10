package com.crm.service.serviceImpl.unit.messageServiceImpl;


import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.MessageServiceImpl;
import com.crm.service.serviceImpl.util.MessageServiceTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateMessageTests {
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
        List<MessageRole> roles = new ArrayList<>();
        roles.add(messageRole);
        attachment = MessageServiceTestDataHelper.createAttachment();
        message = MessageServiceTestDataHelper.createMessage(messageFolder, messageParticipant, roles, attachment);
        message.setId(1);
    }

    @Test
    void shouldReturnMessage_withUpdatedAttachments() {
        //given
        Attachment attachmentWithoutId = new Attachment();
        attachmentWithoutId.setFilePath("TestFilePath");
        attachmentWithoutId.setType(Attachment.Type.MESSAGE);

        Attachment updatedAttachment = new Attachment();
        updatedAttachment.setId(1);
        updatedAttachment.setFilePath("NewFilePath");
        updatedAttachment.setType(Attachment.Type.MESSAGE);

        Message updatedMessage = new Message();
        updatedMessage.setAttachments(List.of(attachmentWithoutId, updatedAttachment));

        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(attachmentRepository.findById(1)).thenReturn(Optional.of(attachment));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        Message result = underTest.updateMessage(1, updatedMessage);

        //then
        verify(messageRepository, times(1)).findById(1);
        verify(attachmentRepository, times(1)).findById(1);
        verify(messageRepository, times(1)).save(any(Message.class));

        assertThat(result.getAttachments()).hasSize(2);

        Attachment firstAttachment = result.getAttachments().get(0);
        assertThat(firstAttachment.getMessage()).isEqualTo(result);
        assertThat(result.getAttachments()).contains(firstAttachment);

        Attachment secondAttachment = result.getAttachments().get(1);
        assertThat(secondAttachment.getFilePath()).isEqualTo("NewFilePath");
        assertThat(result.getAttachments()).contains(secondAttachment);
    }

    @Test
    void shouldReturnMessage_withUpdatedMessageRoles() {
        // given
        MessageRole messageRoleWithId = messageRole;
        messageRoleWithId.setStatus(MessageRole.RoleStatus.CC);

        MessageRole messageRoleWithoutId = new MessageRole();
        messageRoleWithoutId.setStatus(MessageRole.RoleStatus.TO);
        messageRoleWithoutId.setParticipant(messageParticipant);

        Message updateMessage = new Message();
        updateMessage.setMessageRoles(List.of(messageRoleWithId, messageRoleWithoutId));

        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(messageRoleRepository.findById(1)).thenReturn(Optional.of(messageRoleWithId));
        when(messageParticipantRepository.findById(1)).thenReturn(Optional.of(messageParticipant));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Message result = underTest.updateMessage(1, updateMessage);

        // then
        verify(messageRepository, times(1)).findById(1);
        verify(messageRoleRepository, times(1)).findById(messageRoleWithId.getId());
        verify(messageParticipantRepository, times(2)).findById(messageParticipant.getId());
        verify(messageRepository, times(1)).save(any(Message.class));

        assertThat(result.getMessageRoles()).hasSize(2);

        MessageRole updatedRole = result.getMessageRoles().get(0);
        assertThat(updatedRole.getParticipant()).isNotNull();
        assertThat(updatedRole.getStatus()).isEqualTo(MessageRole.RoleStatus.CC);
        assertThat(result.getMessageRoles().get(0)).isEqualTo(updatedRole);

        MessageRole addedRole = result.getMessageRoles().get(1);
        assertThat(addedRole.getStatus()).isEqualTo(MessageRole.RoleStatus.TO);
        assertThat(addedRole.getParticipant()).isEqualTo(messageParticipant);
        assertThat(result.getMessageRoles().get(1)).isEqualTo(addedRole);
    }

    @Test
    void shouldUpdateMessageFolders() {
        // given
        MessageFolder existingFolder = new MessageFolder();
        existingFolder.setId(1);
        existingFolder.setName("Existing Folder");
        message.setMessageFolders(new ArrayList<>(List.of(existingFolder)));

        MessageFolder newFolder = new MessageFolder();
        newFolder.setId(2);
        newFolder.setName("New Folder");

        Message updatedMessage = new Message();
        updatedMessage.setMessageFolders(List.of(existingFolder, newFolder));

        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(messageFolderRepository.findById(1)).thenReturn(Optional.of(existingFolder));
        when(messageFolderRepository.findById(2)).thenReturn(Optional.of(newFolder));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Message result = underTest.updateMessage(1, updatedMessage);

        // then
        assertThat(result.getMessageFolders()).hasSize(2);
        assertThat(result.getMessageFolders()).contains(existingFolder, newFolder);
    }

    @Test
    void shouldThrowException_whenMessageNotFound() {
        // given
        when(messageRepository.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.updateMessage(1, message))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Message not found for ID: 1");
    }

    @Test
    void shouldThrowException_whenFolderNotFound() {
        // given
        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(messageFolderRepository.findById(anyInt())).thenReturn(Optional.empty());

        Message testMessage = new Message();
        MessageFolder missingFolder = new MessageFolder();
        missingFolder.setId(1);
        testMessage.setMessageFolders(List.of(missingFolder));

        // when & then
        assertThatThrownBy(() -> underTest.updateMessage(1, testMessage))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Folder not found for ID: 1");
    }

    @Test
    void shouldThrowException_whenMessageRoleNotFound() {
        // given
        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(messageRoleRepository.findById(anyInt())).thenReturn(Optional.empty());

        Message testMessage = new Message();
        MessageRole missingRole = new MessageRole();
        missingRole.setId(1);
        testMessage.setMessageRoles(List.of(missingRole));

        // when & then
        assertThatThrownBy(() -> underTest.updateMessage(1, testMessage))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("MessageRole not found for ID: 1");
    }

    @Test
    void shouldThrowException_whenAttachmentNotFound() {
        // given
        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(attachmentRepository.findById(anyInt())).thenReturn(Optional.empty());

        Message testMessage = new Message();
        Attachment missingAttachment = new Attachment();
        missingAttachment.setId(1);
        missingAttachment.setType(Attachment.Type.MESSAGE);
        testMessage.setAttachments(List.of(missingAttachment));

        // when & then
        assertThatThrownBy(() -> underTest.updateMessage(1, testMessage))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Attachment not found for ID: 1");
    }

    @Test
    void shouldThrowException_whenParticipantNotFound() {
        // given
        when(messageRepository.findById(1)).thenReturn(Optional.of(message));
        when(messageParticipantRepository.findById(anyInt())).thenReturn(Optional.empty());

        Message testMessage = new Message();
        MessageParticipant missingParticipant = new MessageParticipant();
        missingParticipant.setId(1);

        MessageRole roleWithMissingParticipant = new MessageRole();
        roleWithMissingParticipant.setParticipant(missingParticipant);

        testMessage.setMessageRoles(List.of(roleWithMissingParticipant));

        // when & then
        assertThatThrownBy(() -> underTest.updateMessage(1, testMessage))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Participant not found for ID: 1");
    }
}
