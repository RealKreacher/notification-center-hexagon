package com.wandera.hw.notificationcenter.user.core.model;


import jakarta.validation.constraints.NotEmpty;

public record DeleteNotificationCommand(@NotEmpty String userId, @NotEmpty String notificationId) {
}
