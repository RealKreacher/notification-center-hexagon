package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;

import java.util.List;
import java.util.Map;

public interface GetNotifications {
    Map<NotificationType, List<Notification>> handle(UserNotificationsQuery query);

}
