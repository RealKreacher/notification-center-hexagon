package com.wandera.hw.notificationcenter.user.core.port;

import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;
import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotifications;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetUserNotificationFacade implements GetUserNotifications {

    private final UserNotificationRepository repository;

    @Override
    public List<Notification> handle(UserNotificationsQuery command) {
        return repository.findUserNotifications(command.userId());
    }
}
