package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.GetUserNotificationsCommand;
import com.wandera.hw.notificationcenter.user.core.model.Notification;

import java.util.List;

public interface GetUserNotifications {

    List<Notification> handle(GetUserNotificationsCommand command);
}
