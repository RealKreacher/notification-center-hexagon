package com.wandera.hw.notificationcenter.user.core.model;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public record NotificationId(@NotEmpty UUID notificationId) {
}
