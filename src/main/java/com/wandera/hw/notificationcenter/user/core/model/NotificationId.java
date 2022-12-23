package com.wandera.hw.notificationcenter.user.core.model;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public record NotificationId(@NotEmpty UUID notificationId) {

    public static NotificationId of(@NotEmpty String notificationId) {
        return new NotificationId(UUID.fromString(notificationId));
    }

    @Override
    public String toString() {
        return notificationId.toString();
    }
}
