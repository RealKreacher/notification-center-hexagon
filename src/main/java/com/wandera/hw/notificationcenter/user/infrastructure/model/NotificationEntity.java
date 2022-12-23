package com.wandera.hw.notificationcenter.user.infrastructure.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationId;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
                notificationDetail
        );
    }

}
