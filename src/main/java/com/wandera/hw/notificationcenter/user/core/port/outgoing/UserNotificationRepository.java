package com.wandera.hw.notificationcenter.user.core.port.outgoing;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserNotificationRepository {

    Map<NotificationType, List<Notification>> findUserNotifications(String userId);

    Optional<Notification> findUserNotification(String userId, String notificationId);

    boolean updateNotification(String notificationId, String userId, Object newValue, String fieldName);
}
