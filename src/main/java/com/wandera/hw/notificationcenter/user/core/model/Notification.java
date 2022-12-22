package com.wandera.hw.notificationcenter.user.core.model;

import com.wandera.hw.notificationcenter.user.core.model.NotificationId;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notification {

    // ToDo create Notification detail that will be record
    private final NotificationId notificationId;
    private final UserId userId;
    private final LocalDateTime notificationDate;
    private final NotificationType notificationType;
    private final String notificationTitle;
    private final String notificationDetail;
    private boolean read;

    public Notification(NotificationId notificationId, UserId userId, LocalDateTime notificationDate, NotificationType notificationType, String notificationTitle, String notificationDetail) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.notificationDate = notificationDate;
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.notificationDetail = notificationDetail;
        this.read = false;
    }

   public void markAsRead() {
        read = true;
   }
}
