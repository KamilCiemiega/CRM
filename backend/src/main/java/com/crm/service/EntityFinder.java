package com.crm.service;

import com.crm.entity.Attachment;
import com.crm.entity.Message;
import com.crm.entity.UserNotification;
import com.crm.exception.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface EntityFinder {

    default <T> T findEntity(JpaRepository<T, Integer> repository, int entityId, String entityName) {
        return repository.findById(entityId)
                .orElseThrow(() -> new NoSuchEntityException(entityName + " not found for ID: " + entityId));
    }

    default <T> List<T> findEntities(List<T> entities, JpaRepository<T, Integer> repository, String entityName) {
        List<T> foundEntities = new ArrayList<>();
        for (T entity : entities) {
            Integer id = extractId(entity);
            T foundEntity = findEntity(repository, id, entityName);
            foundEntities.add(foundEntity);
        }
        return foundEntities;
    }

    private Integer extractId(Object entity) {
        if (entity instanceof Message) {
            return ((Message) entity).getId();
        } else if (entity instanceof UserNotification) {
            return ((UserNotification) entity).getId();
        } else if (entity instanceof Attachment) {
            return ((Attachment) entity).getId();
        }
        throw new IllegalArgumentException("Unknown entity type");
    }

    default Integer extractIdPublic(Object entity) {
        return extractId(entity);
    }
}
