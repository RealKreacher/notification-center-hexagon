package com.wandera.hw.notificationcenter.user.infrastructure;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchUserException;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class UserNotificationInMemoryAdapter implements UserNotificationRepository {

    private final Map<String, List<NotificationEntity>> notifications;

    public UserNotificationInMemoryAdapter(NotificationCSVLoader csvLoader) {
        notifications = csvLoader.loadNotifications();
    }

    /*

     */
    @Override
    public List<Notification> findUserNotifications(String userId) {
        return findUserNotificationEntities(userId)
                .stream()
                .map(NotificationEntity::toDomainNotification)
                .sorted(Notification::compareByDate)
                .collect(Collectors.groupingBy(Notification::type))
                .values()
                .stream()
                .reduce((list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                })
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<Notification> findUserNotification(String userId, String notificationId) {
        return findNotificationEntity(userId, notificationId)
                .map(NotificationEntity::toDomainNotification);
    }

    /*
        Currently the whole notification entity object is replaced.
        If needed reflection can be used to set single attribute to different value though it is not so nice.
        Also, more coupling with Notification domain object can be introduced as the NotificationEntity structure
        will probably be leaked to the domain level.
     */
    @Override
    public boolean updateNotification(Notification notification) {
        var userID = notification.userIdAsString();
        var userNotifications = findUserNotificationEntities(userID);

        int entityIndex = findNotificationIndex(notification, userNotifications);

        if (entityIndex < 0) {
            return false;
        }

        userNotifications.set(entityIndex, NotificationEntity.of(notification));
        notifications.put(userID, userNotifications);

        return true;
    }

    @Override
    public boolean deleteNotification(String userId, String notificationId) {
        return findUserNotificationEntities(userId)
                .removeIf(notification -> notification.getNotificationId().equals(notificationId));
    }

    private Optional<NotificationEntity> findNotificationEntity(String userId, String notificationId) {
        return findUserNotificationEntities(userId)
                .stream()
                .filter(notificationEntity -> Objects.equals(notificationEntity.getNotificationId(), notificationId))
                .findFirst();
    }

    private int findNotificationIndex(Notification notification, List<NotificationEntity> userNotifications) {
        return IntStream.range(0, userNotifications.size())
                .filter(i -> userNotifications.get(i).equals(notification))
                .findFirst()
                .orElse(-1);
    }

    /*
        If there is no user with given ID exception is thrown.
        The exception is processed in UserNotificationAdvice and error response is generated.
     */
    private List<NotificationEntity> findUserNotificationEntities(String userId) {
        if (notifications.containsKey(userId)) {
            return notifications.get(userId);
        } else {
            log.warn("No user with id: {}", userId);
            throw new NoSuchUserException("Invalid user ID");
        }
    }
}
