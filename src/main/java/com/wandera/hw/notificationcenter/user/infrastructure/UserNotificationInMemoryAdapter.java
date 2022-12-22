package com.wandera.hw.notificationcenter.user.infrastructure;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserNotificationInMemoryAdapter implements UserNotificationRepository {

    private final Map<UserId, List<NotificationEntity>> notifications;

    public UserNotificationInMemoryAdapter() {
        notifications = new HashMap<>();
    }

    @Override
    public List<Notification> findUserNotifications(UserId userId) {
        return notifications.get(userId).stream()
                .map(NotificationEntity::toDomainNotification)
                .toList();
    }
}
