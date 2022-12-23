package com.wandera.hw.notificationcenter.user.core.port;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotifications;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GetUserNotificationFacade implements GetNotifications {

    private final UserNotificationRepository repository;

    @Override
    public Map<NotificationType, List<Notification>> handle(UserNotificationsQuery query) {
        var userId = query
                .userId()
                .userId();

        return repository.findUserNotifications(userId);
    }
}
