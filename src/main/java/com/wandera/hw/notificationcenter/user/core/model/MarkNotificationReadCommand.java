package com.wandera.hw.notificationcenter.user.core.model;

import javax.validation.constraints.NotEmpty;

public record MarkNotificationReadCommand(@NotEmpty String userId, @NotEmpty String notificationId) {
}
