package com.crm.service.serviceImpl.unit.message;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.serviceImpl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceImplTest {
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

    protected MessageFolder messageFolder;
    protected MessageParticipant messageParticipant;
    protected MessageRole messageRole;
    protected Attachment attachment;
    protected Message message;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        List<MessageRole> messageRoles = new ArrayList<>();

        messageFolder = new MessageFolder();
        messageFolder.setId(1);
        messageFolder.setName("INBOX");
        messageFolder.setFolderType(MessageFolder.FolderType.SYSTEM);

        messageParticipant = new MessageParticipant();
        messageParticipant.setId(1);
        messageParticipant.setType(MessageParticipant.ParticipantType.USER);

        messageRole = new MessageRole();
        messageRole.setId(1);
        messageRole.setStatus(MessageRole.RoleStatus.TO);
        messageRole.setParticipant(messageParticipant);
        messageRoles.add(messageRole);
        messageParticipant.setMessageRoles(messageRoles);

        attachment = new Attachment();
        attachment.setId(1);
        attachment.setType(Attachment.Type.MESSAGE);
        attachment.setFilePath("Test file path");

        message = new Message();
        message.setSubject("Test message");
        message.setBody("Test body for new message");
        message.setStatus(Message.Status.NEW);

        message.getAttachments().add(attachment);
        message.getMessageFolders().add(messageFolder);
        message.getMessageRoles().add(messageRole);
    }

//    @Test
//    void testSaveLogic() {
//        new SaveTests().runTests(this);
//    }

    @Test
    void testUpdateLogic() {
        new UpdateTests().runTests(this);
    }
}
