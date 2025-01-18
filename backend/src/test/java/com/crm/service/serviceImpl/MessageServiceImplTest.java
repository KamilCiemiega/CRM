package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageServiceImplTest {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    AttachmentRepository attachmentRepository;
    @Mock
    MessageFolderRepository messageFolderRepository;
    @Mock
    MessageRoleRepository messageRoleRepository;
    @Mock
    MessageParticipantRepository messageParticipantRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    private MessageServiceImpl underTest;

    private User userData;
    private MessageFolder messageFolderData;
    private MessageParticipant messageParticipantData;
    private MessageRole messageRoleData;
    private Attachment attachmentData;
    private Message messageData;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Brick");
        user.setEmail("john@gmail.com");

        MessageFolder messageFolder = new MessageFolder();
        messageFolder.setId(1);
        messageFolder.setName("INBOX");
        messageFolder.setFolderType(MessageFolder.FolderType.SYSTEM);
        messageFolder.setUser(user);
        user.getMessageFolders().add(messageFolder);

        MessageParticipant messageParticipant = new MessageParticipant();
        messageParticipant.setId(1);
        messageParticipant.setType(MessageParticipant.ParticipantType.USER);
        messageParticipant.setUser(user);

        MessageRole messageRole = new MessageRole();
        messageRole.setStatus(MessageRole.RoleStatus.TO);

        Attachment attachment = new Attachment();
        attachment.setType(Attachment.Type.MESSAGE);
        attachment.setFilePath("Test file path");

        Message message = new Message();
        message.setSubject("Test message");
        message.setBody("Test body for new message");
        message.setStatus(Message.Status.NEW);

        userData = user;
        messageFolderData = messageFolder;
        messageParticipantData = messageParticipant;
        messageRoleData = messageRole;
        attachmentData = attachment;
        message.getMessageFolders().add(messageFolder);
        message.getAttachments().add(attachment);
        message.getMessageRoles().add(messageRole);
        messageData = message;
    }

    @Test
    void saveMessage_shouldReturnMessage_whenMessageExists(){
        //given
        when(messageFolderRepository.findById(1)).thenReturn(Optional.of(messageFolderData));
        when(messageParticipantRepository.findById(1)).thenReturn(Optional.of(messageParticipantData));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //when
        Message savedMessage = underTest.save(messageData);
        System.out.println(savedMessage);
        //then
        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getAttachments().size()).isEqualTo(1);
        assertThat(savedMessage.getAttachments().get(0).getMessage().getId()).isEqualTo(1);
    }

}
