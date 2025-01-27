package com.crm.service;

import com.crm.entity.Attachment;
import com.crm.entity.Message;
import com.crm.entity.Ticket;
import com.crm.entity.UserNotification;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.TicketServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface EntityFinder {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EntityFinder.class);

    default <T> T findEntity(JpaRepository<T, Integer> repository, int entityId, String entityName) {
        return repository.findById(entityId)
                .orElseThrow(() -> new NoSuchEntityException(entityName + " not found for ID: " + entityId));
    }

    default <T> List<T> findEntities(List<T> entities, JpaRepository<T, Integer> repository, String entityName) {
        List<T> foundEntities = new ArrayList<>();

        for (T entity : entities) {
            Integer id = extractId(entity);
            try {
                T foundEntity = findEntity(repository, id, entityName);
                foundEntities.add(foundEntity);
            }catch (NoSuchEntityException e){
                logger.info(e.getMessage());
            }
        }
        return foundEntities;
    }

    private Integer extractId(Object entity) {
        if (entity instanceof Message message) {
            return message.getId();
        } else if (entity instanceof UserNotification userNotification) {
            return userNotification.getId();
        } else if (entity instanceof Attachment attachment) {
            return attachment.getId();
        } else if (entity instanceof Ticket ticket) {
            return ticket.getId();
        }
        throw new IllegalArgumentException("Unknown entity type");
    }

    default Integer extractIdPublic(Object entity) {
        return extractId(entity);
    }
}
