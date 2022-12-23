package com.wandera.hw.notificationcenter.user.core.port.outgoing;

import com.wandera.hw.notificationcenter.user.core.model.Notification;

import java.util.List;
import java.util.Optional;

public interface UserNotificationRepository {

    List<Notification> findUserNotifications(String userId);

    Optional<Notification> findUserNotification(String userId, String notificationId);

}
