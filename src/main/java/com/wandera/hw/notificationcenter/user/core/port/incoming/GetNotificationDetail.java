package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationDetailQuery;

import java.util.Optional;

public interface GetNotificationDetail {

    /**
     * Find notification with given ID for specific user.
     *
     * @param query containing user id and notification id.
     * @return Notification or empty Optional if the notification was not found.
     */
    Optional<Notification> handle(UserNotificationDetailQuery query);

}
