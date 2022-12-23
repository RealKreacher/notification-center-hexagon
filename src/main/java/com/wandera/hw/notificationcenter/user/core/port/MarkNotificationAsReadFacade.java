package com.wandera.hw.notificationcenter.user.core.port;

import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;
import com.wandera.hw.notificationcenter.user.core.port.incoming.MarkNotificationRead;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarkNotificationAsReadFacade implements MarkNotificationRead {

    private final UserNotificationRepository repository;

    @Override
    public boolean handle(MarkNotificationReadCommand command) {

        // ToDO: this is probably overengeneered. Maybe just call entity.markAsRead. Or find the notification, mark as read and then put it in
        String readFieldName = "read";

        return repository.updateNotification(
                command.notificationId(),
                command.userId(),
                true,
                readFieldName
        );
    }
}
