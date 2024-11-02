package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.enums.MessageSortType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageFolderRepository messageFolderRepository;
    @Mock
    private MessageLocationRepository messageLocationRepository;
    @Mock
    private MessageParticipantRepository messageParticipantRepository;
    @Mock
    private ClientRepository clientRepository;
    private MessageServiceImpl underTest;
    private ModelMapper modelMapper;
    private Message message;

    @BeforeEach
    void setUp() {
        underTest = new MessageServiceImpl(messageRepository, modelMapper, messageFolderRepository, messageLocationRepository, messageParticipantRepository);

        MessageFolder messageFolder = new MessageFolder();
        messageFolder.setId(1);
        messageFolder.setName("INBOX");
        messageFolder.setFolderType(MessageFolder.FolderType.SYSTEM);
        lenient().when(messageFolderRepository.findById(1)).thenReturn(Optional.of(messageFolder));

        Client client = new Client();
        client.setId(1);
        client.setName("Adam");
        client.setSurname("Kowalski");
        client.setEmail("kowalski@gmail.com");
        lenient().when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        MessageParticipant messageParticipant = new MessageParticipant();
        messageParticipant.setId(1);
        messageParticipant.setType(MessageParticipant.ParticipantType.CLIENT);
        messageParticipant.setClient(client);
        lenient().when(messageParticipantRepository.findById(1)).thenReturn(Optional.of(messageParticipant));

        MessageRole messageRole = new MessageRole();
        messageRole.setStatus(MessageRole.RoleStatus.CC);
        messageRole.setParticipant(messageParticipant);

        message = new Message();
        String dateString = "2024-02-11T10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);

        message.setSubject("Test subject");
        message.setBody("Test body");
        message.setSentDate(Timestamp.valueOf(localDateTime));
        message.getMessageFolders().add(messageFolder);
        message.getMessageRoles().add(messageRole);
    }



    @Test
    void save() {
        //given
        //when
        underTest.save(message);

        //then
        verify(messageRepository).save(message);
        verify(messageFolderRepository, times(1)).findById(1);
        verify(messageParticipantRepository, times(1)).findById(1);
    }

    @Test
    void updateMessage() {
        // given
        int messageId = 1;

        Message existingMessage = new Message();
        existingMessage.setId(messageId);
        existingMessage.setSubject("Old subject");
        existingMessage.setBody("Old body");
        existingMessage.setStatus(Message.Status.NEW);

        MessageFolder folder = new MessageFolder();
        folder.setId(1);
        folder.setName("SENT");
        folder.setFolderType(MessageFolder.FolderType.SYSTEM);

        MessageParticipant participant = new MessageParticipant();
        participant.setId(1);
        participant.setType(MessageParticipant.ParticipantType.CLIENT);

        MessageRole role = new MessageRole();
        role.setStatus(MessageRole.RoleStatus.TO);
        role.setParticipant(participant);

        Message updatedMessage = new Message();
        updatedMessage.setSubject("Updated subject");
        updatedMessage.setBody("Updated body");
        updatedMessage.setStatus(Message.Status.REPLY);
        updatedMessage.getMessageFolders().add(folder);
        updatedMessage.getMessageRoles().add(role);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(messageFolderRepository.findById(folder.getId())).thenReturn(Optional.of(folder));
        when(messageParticipantRepository.findById(participant.getId())).thenReturn(Optional.of(participant));

        // when
        underTest.updateMessage(messageId, updatedMessage);

        // then
        verify(messageRepository).findById(messageId);
        verify(messageFolderRepository).findById(folder.getId());
        verify(messageParticipantRepository).findById(participant.getId());

        verify(messageRepository).save(existingMessage);

        assertEquals("Updated subject", existingMessage.getSubject());
        assertEquals("Updated body", existingMessage.getBody());
        assertEquals(Message.Status.REPLY, existingMessage.getStatus());

        assertNotNull(existingMessage.getMessageFolders());
        assertEquals(1, existingMessage.getMessageFolders().size());
        assertEquals(folder, existingMessage.getMessageFolders().get(0));

        assertNotNull(existingMessage.getMessageRoles());
        assertEquals(1, existingMessage.getMessageRoles().size());
        MessageRole updatedRole = existingMessage.getMessageRoles().get(0);
        assertEquals(MessageRole.RoleStatus.TO, updatedRole.getStatus());
        assertEquals(participant, updatedRole.getParticipant());
    }



    @Test
    void findAllMessage() {
        //given
        //when
        List<Message> listOfMessage = underTest.findAllMessage();

        //then
        assertThat(listOfMessage).isNotNull();
    }

    @Test
    void getMessageById() {
        //given
        int messageId = 1;

        Message message = new Message();
        message.setId(messageId);
        message.setSubject("Test subject");
        message.setBody("Test body");
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        //when
        Message foundMessage = underTest.getMessageById(messageId);

        //then
        assertNotNull(foundMessage);
        assertThat(foundMessage.getId()).isEqualTo(messageId);
    }


    @Test
    void deleteMessage() {
        // given
        int messageId = 1;
        Message message = new Message();
        message.setId(messageId);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        // when
        Message deletedMessage = underTest.deleteMessage(messageId);

        // then
        verify(messageLocationRepository).deleteByMessageId(messageId);
        verify(messageRepository).findById(messageId);
        verify(messageRepository).delete(message);

        assertThat(deletedMessage).isEqualTo(message);
    }

    @Test
    void getSortedMessages() {
        // given
        int folderId = 1;
        MessageSortType sortType = MessageSortType.SUBJECT;
        String orderType = "ASC";

        Message message1 = new Message();
        message1.setSubject("A Message");

        Message message2 = new Message();
        message2.setSubject("B Message");

        List<Message> messages = List.of(message1, message2);
        when(messageRepository.findMessagesByFolderId(eq(folderId), any(Sort.class))).thenReturn(messages);

        // when
        List<Message> result = underTest.getSortedMessages(folderId, sortType, orderType);

        // then
        verify(messageRepository).findMessagesByFolderId(folderId, Sort.by(Sort.Direction.ASC, "subject"));
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(message1);
        assertThat(result.get(1)).isEqualTo(message2);

    }
}