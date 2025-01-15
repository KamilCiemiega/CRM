package com.crm.utils;

import com.crm.dao.AttachmentRepository;
import com.crm.dao.UserNotificationRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Attachment;
import com.crm.entity.User;
import com.crm.entity.UserNotification;
import com.crm.service.EntityFinder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.BiConsumer;

public class EntityProcessorHelper implements EntityFinder {
    public <R> List<Attachment> processAttachments(
            List<Attachment> attachments,
            R parentEntity,
            JpaRepository<Attachment, Integer> attachmentRepository,
            BiConsumer<Attachment, R> setParent) {

        return attachments.stream()
                .map(attachment -> {
                    if (attachment.getId() != null) {
                        Attachment managedAttachment = findEntity(attachmentRepository, attachment.getId(), "Attachment");
                        setParent.accept(managedAttachment, parentEntity);
                        return managedAttachment;
                    } else {
                        setParent.accept(attachment, parentEntity);
                        return attachment;
                    }
                })
                .toList();
    }
    public <R> List<UserNotification> processUserNotifications(
            List<UserNotification> notifications,
            R parentEntity,
            JpaRepository<UserNotification, Integer> userNotificationRepository,
            JpaRepository<User, Integer> userRepository,
            BiConsumer<UserNotification, R> setParent) {

        return notifications.stream()
                .map(notification -> {
                    if (notification.getId() != null) {
                        UserNotification managedNotification = findEntity(userNotificationRepository, notification.getId(), "Notification");
                        setParent.accept(managedNotification, parentEntity);
                        return managedNotification;
                    } else {
                        User managedUser = findEntity(userRepository, notification.getUser().getId(), "User");
                        notification.setUser(managedUser);
                        setParent.accept(notification, parentEntity);
                        return notification;
                    }
                })
                .toList();
    }


}
