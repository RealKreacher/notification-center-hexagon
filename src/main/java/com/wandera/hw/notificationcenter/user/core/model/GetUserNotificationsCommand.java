package com.wandera.hw.notificationcenter.user.core.model;

import javax.validation.constraints.NotEmpty;

public record GetUserNotificationsCommand(UserId userId) {
}
