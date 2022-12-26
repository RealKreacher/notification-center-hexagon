package com.wandera.hw.notificationcenter.user.application.model;

import java.time.LocalDateTime;

public record Notification(String id,
                           LocalDateTime time,
                           String type,
                           boolean read) {

    public static Notification of(com.wandera.hw.notificationcenter.user.core.model.Notification notification) {
        return new Notification(
                notification.notificationIdAsString(),
                notification.date(),
                notification.type().name(),
                notification.read());
    }
}
