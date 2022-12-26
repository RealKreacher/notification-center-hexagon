package com.wandera.hw.notificationcenter.user.application.model;

import java.time.LocalDateTime;

public record Notification(String notificationId,
                           LocalDateTime notificationTime,
                           String notificationType,
                           boolean read) {

    public static Notification of(com.wandera.hw.notificationcenter.user.core.model.Notification notification) {
        return new Notification(
                notification.notificationIdAsString(),
                notification.date(),
                notification.type().name(),
                notification.read());
    }
}
