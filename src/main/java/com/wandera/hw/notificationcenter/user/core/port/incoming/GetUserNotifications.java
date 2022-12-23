package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;

import java.util.List;

public interface GetUserNotifications {
    List<Notification> handle(UserNotificationsQuery query);

}
