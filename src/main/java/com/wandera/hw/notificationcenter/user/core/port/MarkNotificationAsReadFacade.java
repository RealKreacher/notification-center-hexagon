package com.wandera.hw.notificationcenter.user.core.port;

import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;
import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.port.incoming.MarkNotificationRead;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchNotificationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarkNotificationAsReadFacade implements MarkNotificationRead {

    private final UserNotificationRepository repository;

    @Override
    public boolean handle(MarkNotificationReadCommand command) {
        var notification = repository.findUserNotification(command.userId(), command.notificationId());

        return notification
                .map(Notification::markAsRead)
                .map(repository::updateNotification)
                .orElseThrow(() -> new NoSuchNotificationException("No notification with given ID"));
    }
}
