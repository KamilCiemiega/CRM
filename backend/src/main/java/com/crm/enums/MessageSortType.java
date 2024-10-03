package com.crm.enums;

import lombok.Getter;

@Getter
public enum MessageSortType {
    DATE("sentDate"),
    SIZE("size"),
    SUBJECT("subject");

    private final String fieldName;

    MessageSortType(String fieldName) {
        this.fieldName = fieldName;
    }

}
