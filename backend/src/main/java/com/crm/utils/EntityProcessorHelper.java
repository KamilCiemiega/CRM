package com.crm.utils;

import com.crm.entity.Attachment;
import com.crm.entity.Ticket;
import com.crm.entity.User;
import com.crm.entity.UserNotification;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.EntityFinder;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class EntityProcessorHelper implements EntityFinder {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EntityProcessorHelper.class);

//    private <T extends JpaRepository<V, Integer>, V> T catchFindEntitySideEffects (T repository, V entity, String entityName){
//        if (entity instanceof Attachment attachment){

//        }
//
//        try {
//            findEntity(repository)
//
//        }catch (NoSuchEntityException e){
//            logger.info(e.getMessage());
//            return null;
//        }
//    }

    public <R> List<Attachment> processAttachments(
            List<Attachment> attachments,
            R parentEntity,
            JpaRepository<Attachment, Integer> attachmentRepository,
            BiConsumer<Attachment, R> setParent) {

        return attachments.stream()
                .map(attachment -> {
                    if (attachment.getId() != null) {
                        try {
                            Attachment managedAttachment = findEntity(attachmentRepository, attachment.getId(), "Attachment");
                            setParent.accept(managedAttachment, parentEntity);
                            return managedAttachment;
                        }catch (NoSuchEntityException e){
                            logger.info(e.getMessage());
                            return null;
                        }
                    }else {
                        setParent.accept(attachment, parentEntity);
                        return attachment;
                    }
                })
                .filter(Objects::nonNull)
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
