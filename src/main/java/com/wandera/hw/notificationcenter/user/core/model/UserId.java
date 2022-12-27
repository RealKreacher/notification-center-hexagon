package com.wandera.hw.notificationcenter.user.core.model;


import jakarta.validation.constraints.NotEmpty;

public record UserId(@NotEmpty String userId) {

    public static UserId of(String userId) {
        return new UserId(userId);
    }
}
