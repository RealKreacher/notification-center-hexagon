package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationDetailQuery;

import java.util.Optional;

public interface GetNotificationDetail {

    Optional<Notification> handle(UserNotificationDetailQuery query);

}
