package com.wandera.hw.notificationcenter.user.infrastructure.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationId;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class NotificationEntity {

    // TODO: This may have different structure. Decide later
    // This also should be a record
    private final NotificationId notificationId;
    private final UserId userId;
    private final LocalDateTime notificationDate;
    private final NotificationType notificationType;
    private final String notificationTitle;
    private final String notificationDetail;
    private boolean read;

    public Notification toDomainNotification() {
        return new Notification(notificationId,
                userId,
                notificationDate,
                notificationType,
                notificationTitle,
                notificationDetail
        );
    }
}
