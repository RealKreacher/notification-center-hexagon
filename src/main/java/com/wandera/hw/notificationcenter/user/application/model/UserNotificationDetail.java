package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;

import java.util.Optional;

public record UserNotificationDetail(NotificationDetail notificationDetail) {

    public static Optional<UserNotificationDetail> of(Notification notification) {
        return Optional.of(new UserNotificationDetail(NotificationDetail.of(notification)));
    }
}
