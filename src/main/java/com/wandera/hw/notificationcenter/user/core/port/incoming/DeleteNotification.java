package com.wandera.hw.notificationcenter.user.core.port.incoming;

import com.wandera.hw.notificationcenter.user.core.model.DeleteNotificationCommand;

public interface DeleteNotification {

    boolean handle(DeleteNotificationCommand command);
}
