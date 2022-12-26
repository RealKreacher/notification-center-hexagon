package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;

import java.util.List;

public interface GetNotifications {

    /**
     * Find all notifications for given user id.
     * The notifications are grouped by type and sort by date ascending.
     *
     * @param query Query containing user id
     * @return List of all notifications for given user.
     */
    List<Notification> handle(UserNotificationsQuery query);

}
