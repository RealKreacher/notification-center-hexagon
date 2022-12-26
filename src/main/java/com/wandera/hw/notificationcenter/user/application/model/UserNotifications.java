package com.wandera.hw.notificationcenter.user.application.model;

import java.util.List;
import java.util.Optional;

public record UserNotifications(List<Notification> notifications) {

    public static Optional<UserNotifications> of(List<com.wandera.hw.notificationcenter.user.core.model.Notification> notifications) {
        var responseNotifications = notifications.stream()
                .map(Notification::of)
                .toList();

        return Optional.of(new UserNotifications(responseNotifications));
    }
}
