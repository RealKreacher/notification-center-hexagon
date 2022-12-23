package com.wandera.hw.notificationcenter.user.core.port;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationDetailQuery;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotificationDetail;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetUserNotificationDetailFacade implements GetNotificationDetail {

    private final UserNotificationRepository repository;

    @Override
    public Optional<Notification> handle(UserNotificationDetailQuery query) {
        return repository.findUserNotification(query.userId(), query.notificationId());
    }
}
