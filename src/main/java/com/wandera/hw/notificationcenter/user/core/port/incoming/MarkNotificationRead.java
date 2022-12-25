package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;

public interface MarkNotificationRead {

    /**
     * Find notification with given id for the given user and change its <i>read</i> value to true.
     *
     * @param command Command containing user id and notification id
     * @return boolean indicator if the marking process was successful
     */
    boolean handle(MarkNotificationReadCommand command);
}
