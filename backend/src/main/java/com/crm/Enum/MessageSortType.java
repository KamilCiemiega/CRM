package com.crm.Enum;

public enum MessageSortType {
    DATE("sentDate"),
    SIZE("size");

    private final String fieldName;

    MessageSortType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
