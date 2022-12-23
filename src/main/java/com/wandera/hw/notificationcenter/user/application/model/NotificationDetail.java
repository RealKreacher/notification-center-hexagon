package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import lombok.Builder;

@Builder
public record NotificationDetail(String notificationId,
                                 String userId,
                                 String date,
                                 String type,
                                 String title,
                                 String detail,
                                 boolean read) {

    public static NotificationDetail of(Notification notification) {
        return NotificationDetail.builder()
                .notificationId(notification.notificationId()
                        .notificationId()
                        .toString())
                .userId(notification.userId()
                        .userId())
                .date(notification.date().toString())
                .type(notification.type().name())
                .title(notification.title())
                .detail(notification.detail())
                .read(notification.read())
                .build();
    }
}
