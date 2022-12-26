package com.wandera.hw.notificationcenter.user.infrastructure.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationId;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class NotificationEntity {

    private String notificationId;
    private String userId;
    private LocalDateTime notificationDate;
    private String notificationType;
    private String notificationTitle;
    private String notificationDetail;
    private boolean read;

    public Notification toDomainNotification() {
        return new Notification(new NotificationId(UUID.fromString(notificationId)),
                new UserId(userId),
                notificationDate,
                NotificationType.valueOf(notificationType),
                notificationTitle,
                notificationDetail,
                read
        );
    }

    public static NotificationEntity of(Notification notification) {
        return new NotificationEntity(
                notification.notificationIdAsString(),
                notification.userIdAsString(),
                notification.date(),
                notification.type().name(),
                notification.title(),
                notification.detail(),
                notification.read());
    }

    public boolean equalToDomain(Notification notification) {
        return notification.notificationIdAsString().equals(notificationId);
    }
}
