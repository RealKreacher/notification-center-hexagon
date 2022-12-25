package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;

import java.util.Optional;

public record UserNotificationDetailResponse(NotificationDetail notificationDetail) {

    public static Optional<UserNotificationDetailResponse> of(Notification notification) {
        return Optional.of(new UserNotificationDetailResponse(NotificationDetail.of(notification)));
    }
}
