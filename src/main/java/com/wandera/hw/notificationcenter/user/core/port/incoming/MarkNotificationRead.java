package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;

public interface MarkNotificationRead {

    boolean handle(MarkNotificationReadCommand command);
}
