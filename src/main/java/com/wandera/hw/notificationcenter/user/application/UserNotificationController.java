package com.wandera.hw.notificationcenter.user.application;

import com.wandera.hw.notificationcenter.user.application.model.UserNotificationsResponse;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotifications;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "User notifications api")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserNotificationController {

    private final GetUserNotifications getUserNotifications;

    @GetMapping("/notifications")
    public ResponseEntity<UserNotificationsResponse> notifications(@RequestParam String userId) {
        var query = new UserNotificationsQuery(UserId.of(userId));
        var notifications = getUserNotifications.handle(query);
        return ResponseEntity.of(UserNotificationsResponse.of(notifications));
    }

    @GetMapping("test")
    public String test() {
        return "Hello";
    }
}
