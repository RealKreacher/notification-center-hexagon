package com.wandera.hw.notificationcenter.user.core.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notification {

    // ToDo create Notification detail that will be record
    private final NotificationId notificationId;
    private final UserId userId;
    private final LocalDateTime date;
    private final NotificationType type;
    private final String title;
    private final String detail;
    private boolean read;

    public Notification(NotificationId notificationId, UserId userId, LocalDateTime notificationDate, NotificationType type, String title, String detail) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.date = notificationDate;
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.read = false;
    }

    public void markAsRead() {
        read = true;
    }

    public int compareByDate(Notification otherNotification) {
        return date.compareTo(otherNotification.date);
    }

    public int compareByType(Notification otherNotification) {
        return type.compareTo(otherNotification.type);
    }

}
