package com.wandera.hw.notificationcenter.user.infrastructure;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserNotificationInMemoryAdapter implements UserNotificationRepository {

    private final Map<String, List<NotificationEntity>> notifications;

    public UserNotificationInMemoryAdapter(NotificationCSVLoader csvLoader) {
        // TODO: make this nicer
        var notificationList = csvLoader.loadNotifications();
        notifications = new HashMap<>();
        notificationList.forEach(notification -> {
            var userId = notification.getUserId();
            var newEntry = notifications.getOrDefault(userId, new ArrayList<>());
            newEntry.add(notification);
                notifications.put(notification.getUserId(), newEntry);
        });
    }

    @Override
    public List<Notification> findUserNotifications(UserId userId) {

        // ToDO: map shoud probably containt userId object.
        // ToDo if user is not in the map the Errro response should be shown
        return notifications.getOrDefault(userId.userId(), Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .map(NotificationEntity::toDomainNotification)
                .toList();
    }
}
