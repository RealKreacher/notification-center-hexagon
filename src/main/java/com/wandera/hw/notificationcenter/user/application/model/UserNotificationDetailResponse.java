package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;

public record UserNotificationDetailResponse(NotificationDetail notificationDetail) {

    public static UserNotificationDetailResponse of(Notification notification) {
        return new UserNotificationDetailResponse(NotificationDetail.of(notification));
    }
}
