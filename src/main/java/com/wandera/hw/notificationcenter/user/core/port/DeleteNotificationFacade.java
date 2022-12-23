package com.wandera.hw.notificationcenter.user.core.port;

import com.wandera.hw.notificationcenter.user.core.model.DeleteNotificationCommand;
import com.wandera.hw.notificationcenter.user.core.port.incoming.DeleteNotification;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteNotificationFacade implements DeleteNotification {

    private final UserNotificationRepository repository;

    @Override
    public boolean handle(DeleteNotificationCommand command) {
        return repository.deleteNotification(command.userId(), command.notificationId());
    }
}
