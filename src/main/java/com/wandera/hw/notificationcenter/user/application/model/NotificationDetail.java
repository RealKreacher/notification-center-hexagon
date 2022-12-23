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
                .notificationId(notification.getNotificationId()
                        .notificationId()
                        .toString())
                .userId(notification.getUserId()
                        .userId())
                .date(notification.getDate().toString())
                .type(notification.getType().name())
                .title(notification.getTitle())
                .detail(notification.getDetail())
                .read(notification.isRead())
                .build();
    }
}
