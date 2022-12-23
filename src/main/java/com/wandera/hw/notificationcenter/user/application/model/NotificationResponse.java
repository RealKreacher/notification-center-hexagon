package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;

import java.time.LocalDateTime;

public record NotificationResponse(String notificationId,
                                   LocalDateTime notificationTime,
                                   boolean read) {

    public static NotificationResponse of(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId().toString(),
                notification.getDate(),
                notification.isRead());
    }
}
