package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;

import java.util.List;
import java.util.Optional;

public record UserNotificationsResponse(List<NotificationResponse> notifications) {

    public static Optional<UserNotificationsResponse> of(List<Notification> notifications) {
        var responseNotifications = notifications.stream()
                .map(NotificationResponse::of)
                .toList();

        // TODO: Maybe create the whole template based od HTTP status codes, etc.
        return Optional.of(new UserNotificationsResponse(responseNotifications));
    }
}
