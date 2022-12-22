package com.wandera.hw.notificationcenter.user.core.port.outgoing;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserId;

import java.util.List;

public interface UserNotificationRepository {

    List<Notification> findUserNotifications(UserId userId);

}
