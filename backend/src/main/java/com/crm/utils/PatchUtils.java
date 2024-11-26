package com.crm.utils;

import org.apache.commons.beanutils.BeanUtils;

public class PatchUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (Exception e) {
            throw new RuntimeException("Error copying properties", e);
        }
    }
}
