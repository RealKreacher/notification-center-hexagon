package com.wandera.hw.notificationcenter.user.core.model;


import jakarta.validation.constraints.NotEmpty;

public record MarkNotificationReadCommand(@NotEmpty String userId, @NotEmpty String notificationId) {
}
