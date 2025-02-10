package com.crm.service.serviceImpl.util;

import com.crm.entity.*;
import java.util.List;

public class MessageServiceTestDataHelper {

    public static MessageFolder createMessageFolder() {
        MessageFolder messageFolder = new MessageFolder();
        messageFolder.setId(1);
        messageFolder.setName("INBOX");
        messageFolder.setFolderType(MessageFolder.FolderType.SYSTEM);
        return messageFolder;
    }

    public static MessageParticipant createMessageParticipant() {
        MessageParticipant participant = new MessageParticipant();
        participant.setId(1);
        participant.setType(MessageParticipant.ParticipantType.USER);
        return participant;
    }

    public static MessageRole createMessageRole(MessageParticipant participant) {
        MessageRole messageRole = new MessageRole();
        messageRole.setId(1);
        messageRole.setStatus(MessageRole.RoleStatus.TO);
        messageRole.setParticipant(participant);
        return messageRole;
    }

    public static Attachment createAttachment() {
        Attachment attachment = new Attachment();
        attachment.setId(1);
        attachment.setType(Attachment.Type.MESSAGE);
        attachment.setFilePath("Test file path");
        return attachment;
    }

    public static Message createMessage(MessageFolder folder, MessageParticipant participant, List<MessageRole> roles, Attachment attachment) {
        Message message = new Message();
        message.setSubject("Test message");
        message.setBody("Test body for new message");
        message.setStatus(Message.Status.NEW);

        message.getAttachments().add(attachment);
        message.getMessageFolders().add(folder);
        message.getMessageRoles().addAll(roles);

        return message;
    }
}
