package com.wandera.hw.notificationcenter.user.domain.port;

import com.wandera.hw.notificationcenter.user.domain.model.Notification;
import com.wandera.hw.notificationcenter.user.domain.model.NotificationId;
import com.wandera.hw.notificationcenter.user.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface NotificationPort {

    List<Notification> getNotifications(UserId userId);

    Optional<Notification> getNotification(NotificationId notificationId);

    boolean hasBeenRead(NotificationId notificationId);

    boolean markAsRead(NotificationId notificationId);

    boolean deleteNotification(NotificationId notificationId);

}


