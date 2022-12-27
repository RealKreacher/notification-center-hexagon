package com.wandera.hw.notificationcenter.user.core.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;


@Builder
public record UserNotificationDetailQuery(@NotEmpty String userId, @NotEmpty String notificationId) {
}
