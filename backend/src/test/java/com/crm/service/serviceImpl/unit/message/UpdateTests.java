package com.crm.service.serviceImpl.unit.message;

import com.crm.dao.MessageParticipantRepository;
import com.crm.entity.Attachment;
import com.crm.entity.Message;
import com.crm.entity.MessageParticipant;
import com.crm.entity.MessageRole;
import com.crm.exception.NoSuchEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateTests {
    public void runTests(MessageServiceImplTest context) {
        shouldThrowException_whenMessageNotFound(context);
//        shouldThrowException_whenAttachmentNotFound(context);
//        shouldThrowException_whenMessageRoleNotFound(context);
//        shouldThrowException_whenMessageParticipantNotFound(context);
//        shouldThrowException_whenMessageFolderNotFound(context);

        shouldReturnMessage_withUpdatedAttachments(context);
        shouldReturnMessage_withUpdatedMessageRoles(context);
    }

    private void shouldReturnMessage_withUpdatedAttachments(MessageServiceImplTest context) {
        //given
        context.message.setId(1);

        Attachment attachmentWithoutId = new Attachment();
        attachmentWithoutId.setFilePath("TestFilePathForAttachmentWithoutId");
        attachmentWithoutId.setType(Attachment.Type.MESSAGE);

        Attachment attachmentWithId = context.attachment;
        attachmentWithId.setFilePath("NewFilePath");

        Message updatedMessage = new Message();
        updatedMessage.setAttachments(List.of(attachmentWithoutId, attachmentWithId));

        when(context.messageRepository.findById(1)).thenReturn(Optional.of(context.message));
        when(context.attachmentRepository.findById(1)).thenReturn(Optional.of(context.attachment));
        when(context.messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message savedMessage = invocation.getArgument(0);
            attachmentWithoutId.setId(2);
            return savedMessage;
        });

        //when
        Message results = context.underTest.updateMessage(1, updatedMessage);

        //then
        verify(context.messageRepository, times(1)).findById(1);
        verify(context.attachmentRepository, times(1)).findById(1);
        verify(context.messageRepository, times(1)).save(any(Message.class));

        assertThat(results.getAttachments().size()).isEqualTo(2);
        assertThat(results.getAttachments().get(1).getFilePath()).isEqualTo("NewFilePath");
        assertThat(results.getAttachments().get(0).getMessage()).isEqualTo(context.message);
    }

    private void shouldReturnMessage_withUpdatedMessageRoles(MessageServiceImplTest context) {
        // given
        Message existingMessage = context.message;
        existingMessage.setId(1);

        MessageRole messageRoleWithId = context.messageRole;
        messageRoleWithId.setStatus(MessageRole.RoleStatus.CC);

        MessageRole messageRoleWithoutId = new MessageRole();
        messageRoleWithoutId.setStatus(MessageRole.RoleStatus.TO);
        messageRoleWithoutId.setParticipant(context.messageParticipant);

        Message updateMessage = new Message();
        updateMessage.setMessageRoles(List.of(messageRoleWithId, messageRoleWithoutId));

        when(context.messageRepository.findById(1)).thenReturn(Optional.of(existingMessage));
        when(context.messageRoleRepository.findById(1)).thenReturn(Optional.of(messageRoleWithId));
        when(context.messageParticipantRepository.findById(1)).thenReturn(Optional.of(context.messageParticipant));
        when(context.messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Message result = context.underTest.updateMessage(1, updateMessage);

        // then
        verify(context.messageRepository, times(1)).findById(1);
        verify(context.messageRoleRepository, times(1)).findById(messageRoleWithId.getId());
        verify(context.messageParticipantRepository, times(2)).findById(context.messageParticipant.getId());
        verify(context.messageRepository, times(1)).save(any(Message.class));

        assertThat(result.getMessageRoles()).hasSize(2);

        MessageRole updatedRole = result.getMessageRoles().get(0);
        assertThat(updatedRole.getParticipant()).isNotNull();
        assertThat(updatedRole.getStatus()).isEqualTo(MessageRole.RoleStatus.CC);
        assertThat(result.getMessageRoles().get(0)).isEqualTo(updatedRole);

        MessageRole addedRole = result.getMessageRoles().get(1);
        assertThat(addedRole.getStatus()).isEqualTo(MessageRole.RoleStatus.TO);
        assertThat(addedRole.getParticipant()).isEqualTo(context.messageParticipant);
        assertThat(result.getMessageRoles().get(1)).isEqualTo(addedRole);
    }

    private void shouldThrowException_whenMessageNotFound(MessageServiceImplTest context){
        //given
        when(context.messageRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> context.underTest.updateMessage(1, context.message))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Message not found for ID: 1");
    }

//    private void shouldThrowException_whenAttachmentNotFound(MessageServiceImplTest context){
//        //given
//        when(context.attachmentRepository.findById(1)).thenReturn(Optional.empty());
//
//        //when & then
//        assertThatThrownBy(() -> context.underTest.updateMessage(1, context.message))
//                .isInstanceOf(NoSuchEntityException.class)
//                .hasMessageContaining("Attachment not found for ID: 1");
//    }
//
//    private void shouldThrowException_whenMessageRoleNotFound(MessageServiceImplTest context) {
//            // given
//            Message existingMessage = new Message();
//            existingMessage.setId(1);
//
//            when(context.messageRepository.findById(1)).thenReturn(Optional.of(existingMessage));
//            when(context.messageRoleRepository.findById(1)).thenReturn(Optional.empty());
//
//            // when & then
//            assertThatThrownBy(() -> context.underTest.updateMessage(1, context.message))
//                    .isInstanceOf(NoSuchEntityException.class)
//                    .hasMessageContaining("MessageRole not found for ID: 1");
//    }
//
//    private void shouldThrowException_whenMessageParticipantNotFound(MessageServiceImplTest context){
//        //given
//        when(context.messageParticipantRepository.findById(1)).thenReturn(Optional.empty());
//
//        //when & then
//        assertThatThrownBy(() -> context.underTest.updateMessage(1, context.message))
//                .isInstanceOf(NoSuchEntityException.class)
//                .hasMessageContaining("Participant not found for ID: 1");
//    }
//
//    private void shouldThrowException_whenMessageFolderNotFound(MessageServiceImplTest context){
//        //given
//        when(context.messageFolderRepository.findById(1)).thenReturn(Optional.empty());
//
//        //when & then
//        assertThatThrownBy(() -> context.underTest.updateMessage(1, context.message))
//                .isInstanceOf(NoSuchEntityException.class)
//                .hasMessageContaining("Folder not found for ID: 1");
//    }


}
