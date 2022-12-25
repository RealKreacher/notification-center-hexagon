package com.wandera.hw.notificationcenter.user.core.port.outgoing;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserNotificationRepository {

    /**
     * Find user notifications grouped by type and sorted by date ascending.
     * The notifications are grouped and sorted on the infrastructure level as it should make it easier to switch stack.
     * <br>
     * For example sorting and grouping with DB is probably better done on DB level then application level.
     * With this approach there is no need to change the domain.
     * <br>
     * <br>
     * If there is no user for given userId then NoSuchUserException is thrown and proper error response
     * returned to the client.
     *
     * @param userId
     * @return Grouped and sorted notifications for given user
     */
    List<Notification> findUserNotifications(String userId);


    /**
     * Find specified notification if present for given user.
     * <br>
     * If there is no user for given userId then NoSuchUserException is thrown and proper error response
     * returned to the client
     *
     * @param userId
     * @param notificationId
     * @return Specified notification of empty Optional
     */
    Optional<Notification> findUserNotification(String userId, String notificationId);

    /**
     * Update value for specified filed for Notification TODO: Is this really good idea
     * If there is no user for given userId then NoSuchUserException is thrown and proper error response
     * returned to the client
     *
     * @param notificationId
     * @param userId
     * @param newValue
     * @param fieldName
     * @return
     */
    boolean updateNotification(String notificationId, String userId, Object newValue, String fieldName);

    /**
     * Delete specified notification.<br>
     * <br>
     * If there is no user for given userId then NoSuchUserException is thrown and proper error response
     * returned to the client
     *
     * @param userId
     * @param notificationId
     * @return if false then notification was not found. Otherwise, the notification was deleted successfully.
     */
    boolean deleteNotification(String userId, String notificationId);
}
