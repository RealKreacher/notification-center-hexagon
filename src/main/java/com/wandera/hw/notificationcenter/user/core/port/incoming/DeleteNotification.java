package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.DeleteNotificationCommand;

public interface DeleteNotification {

    /**
     * Delete notification with given id for specified user.
     *
     * @param command contain user id and notification id.
     * @return true if deletion was successful or false otherwise.
     */
    boolean handle(DeleteNotificationCommand command);
}
