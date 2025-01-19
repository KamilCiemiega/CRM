package com.crm.service.serviceImpl.unit.message;

import com.crm.entity.Message;
import com.crm.exception.NoSuchEntityException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveTests {
    public void runTests(MessageServiceImplTest context){
        shouldReturnMessage_withCorrectDataAndRelations(context);
        shouldThrowException_whenFolderNotFound(context);
        shouldThrowException_whenParticipantNotFound(context);
    }

    private void shouldReturnMessage_withCorrectDataAndRelations(MessageServiceImplTest context){
        //given
        when(context.messageFolderRepository.findById(1)).thenReturn(Optional.of(context.messageFolder));
        when(context.messageParticipantRepository.findById(1)).thenReturn(Optional.of(context.messageParticipant));
        when(context.messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message savedMessage = invocation.getArgument(0);
            savedMessage.setId(1);
            return savedMessage;
        });

        //when
        Message savedMessage = context.underTest.save(context.message);

        //then
        verify(context.messageFolderRepository, times(1)).findById(1);
        verify(context.messageParticipantRepository, times(1)).findById(1);
        verify(context.messageRepository, times(1)).save(any(Message.class));

        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getId()).isEqualTo(1);

        //Attachment
        assertThat(savedMessage.getAttachments()).isNotNull();
        assertThat(savedMessage.getAttachments().size()).isEqualTo(1);
        assertThat(savedMessage.getAttachments()).contains(context.attachment);
        assertThat(context.attachment.getMessage()).isEqualTo(savedMessage);

        //MessageFolder
        assertThat(savedMessage.getMessageFolders()).isNotNull();
        assertThat(savedMessage.getMessageFolders().size()).isEqualTo(1);
        assertThat(savedMessage.getMessageFolders()).contains(context.messageFolder);
        assertThat(context.messageFolder.getMessages()).contains(savedMessage);

        //MessageRole
        assertThat(savedMessage.getMessageRoles()).isNotNull();
        assertThat(savedMessage.getMessageFolders().size()).isEqualTo(1);
        assertThat(savedMessage.getMessageRoles()).contains(context.messageRole);
        assertThat(context.messageRole.getMessage()).isEqualTo(savedMessage);
        assertThat(context.messageRole.getParticipant()).isEqualTo(context.messageParticipant);
        assertThat(context.messageParticipant.getMessageRoles()).contains(context.messageRole);
    }

    private void shouldThrowException_whenFolderNotFound(MessageServiceImplTest context) {
        // given
        when(context.messageFolderRepository.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> context.underTest.save(context.message))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Folder not found for ID: 1");
    }

    private void shouldThrowException_whenParticipantNotFound(MessageServiceImplTest context) {
        // given
        when(context.messageFolderRepository.findById(1)).thenReturn(Optional.of(context.messageFolder));
        when(context.messageParticipantRepository.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> context.underTest.save(context.message))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Participant not found for ID: 1");
    }
}
