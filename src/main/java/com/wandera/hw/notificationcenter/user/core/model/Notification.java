package com.wandera.hw.notificationcenter.user.core.model;

import lombok.With;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@FieldNameConstants
@With
public record Notification(NotificationId notificationId,
                           UserId userId,
                           LocalDateTime date,
                           NotificationType type,
                           String title,
                           String detail,
                           boolean read) {

    public Notification(NotificationId notificationId,
                        UserId userId,
                        LocalDateTime date,
                        NotificationType type,
                        String title,
                        String detail) {
        this(notificationId, userId, date, type, title, detail, false);
    }

    public Notification markAsRead() {
        return this.withRead(true);
    }

    public int compareByDate(Notification otherNotification) {
        return date.compareTo(otherNotification.date);
    }

    public String notificationIdAsString() {
        return notificationId
                .notificationId()
                .toString();
    }

    public String userIdAsString() {
        return userId.userId();
    }
}
