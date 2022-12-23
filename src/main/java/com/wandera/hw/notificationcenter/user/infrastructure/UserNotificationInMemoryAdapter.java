package com.wandera.hw.notificationcenter.user.infrastructure;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserNotificationInMemoryAdapter implements UserNotificationRepository {

    private final Map<String, List<NotificationEntity>> notifications;

    public UserNotificationInMemoryAdapter(NotificationCSVLoader csvLoader) {
        // TODO: make this nicer
        var notificationList = csvLoader.loadNotifications();

        notifications = new HashMap<>();
        notificationList.forEach(notification -> {
            var userId = notification.getUserId();
            var oldEntry = notifications.getOrDefault(userId, new ArrayList<>());
            oldEntry.add(notification);
            notifications.put(userId, oldEntry);
        });
    }

    /*

     */
    @Override
    public Map<NotificationType, List<Notification>> findUserNotifications(String userId) {
        // ToDO: map shoud probably containt userId object.
        // ToDo if user is not in the map the Errro response should be shown
        return notifications.getOrDefault(userId, Collections.emptyList())
                .stream()
                .map(NotificationEntity::toDomainNotification)
                .sorted(Notification::compareByDate)
                .collect(Collectors.groupingBy(Notification::type));
    }

    @Override
    public Optional<Notification> findUserNotification(String userId, String notificationId) {
        return findNotificationEntity(userId, notificationId)
                .map(NotificationEntity::toDomainNotification);
    }

    @Override
    public boolean updateNotification(String notificationId, String userId, Object newValue, String fieldName) {
        // TODO there should just be exception handled by global aop handler
        return findNotificationEntity(userId, notificationId)
                .map(entity -> entity.update(newValue, fieldName))
                .orElse(false);
    }

    @Override
    public boolean deleteNotification(String userId, String notificationId) {
        return notifications.getOrDefault(userId, Collections.emptyList())
                .removeIf(notification -> notification.getNotificationId().equals(notificationId));
    }

    private Optional<NotificationEntity> findNotificationEntity(String userId, String notificationId) {
        return notifications.getOrDefault(userId, Collections.emptyList()).stream()
                .filter(notificationEntity -> Objects.equals(notificationEntity.getNotificationId(), notificationId))
                .findFirst();
    }
}
