package com.wandera.hw.notificationcenter.user.application.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import lombok.Builder;

import java.util.Optional;

@Builder
public record UserNotificationDetail(String notificationId,
                                     String userId,
                                     String date,
                                     String type,
                                     String title,
                                     String detail,
                                     boolean read) {


    public static Optional<UserNotificationDetail> of(Notification notification) {
        return Optional.of(UserNotificationDetail.builder()
                .notificationId(notification.notificationIdAsString())
                .userId(notification.userIdAsString())
                .date(notification.date().toString())
                .type(notification.type().name())
                .title(notification.title())
                .detail(notification.detail())
                .read(notification.read())
                .build());
    }
}
