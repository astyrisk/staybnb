package com.staybnb.model;

public record Notification(
        int id,
        int tenantId,
        int userId,
        String type,
        String title,
        String message,
        String referenceType,
        int referenceId,
        boolean isRead,
        String createdAt
) {}
