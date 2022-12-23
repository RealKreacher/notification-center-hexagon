package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record UserNotificationsResponse(Map<String, List<NotificationResponse>> notifications) {

    public static Optional<UserNotificationsResponse> of(Map<NotificationType, List<Notification>> notifications) {
        var responseNotifications = notifications.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        entry -> entry.getValue().stream()
                                .map(NotificationResponse::of)
                                .toList()

                ));

        // TODO: Maybe create the whole template based od HTTP status codes, etc.
        return Optional.of(new UserNotificationsResponse(responseNotifications));
    }
}
