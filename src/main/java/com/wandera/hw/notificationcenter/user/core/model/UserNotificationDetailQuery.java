package com.wandera.hw.notificationcenter.user.core.model;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record UserNotificationDetailQuery(@NotEmpty String userId, @NotEmpty String notificationId) {
}
