package com.wandera.hw.notificationcenter.user.application;

import com.wandera.hw.notificationcenter.user.application.model.UserNotificationDetailResponse;
import com.wandera.hw.notificationcenter.user.application.model.UserNotificationsResponse;
import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationDetailQuery;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;
import com.wandera.hw.notificationcenter.user.core.port.MarkNotificationAsReadFacade;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotificationDetail;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotifications;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "User notifications api")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserNotificationController {

    private final GetUserNotifications getUserNotifications;
    private final GetUserNotificationDetail getUserNotificationDetail;
    private final MarkNotificationAsReadFacade markNotificationAsRead;

    @GetMapping("/notifications")
    public ResponseEntity<UserNotificationsResponse> notifications(@RequestParam String userId) {
        var query = new UserNotificationsQuery(UserId.of(userId));
        var notifications = getUserNotifications.handle(query);
        return ResponseEntity.of(UserNotificationsResponse.of(notifications));
    }

    @GetMapping("/notifications/{notificationId}")
    public ResponseEntity<UserNotificationDetailResponse> notificationDetail(@PathVariable String notificationId,
                                                                             @RequestParam String userId) {
        var query = UserNotificationDetailQuery.builder()
                .userId(userId)
                .notificationId(notificationId)
                .build();

        var notificationDetail = getUserNotificationDetail.handle(query);
        var response = notificationDetail.map(UserNotificationDetailResponse::of);
        return ResponseEntity.of(response);
    }

    @PatchMapping("/notifications/{notificationId}")
    public ResponseEntity<HttpStatus> markNotificationAsRead(@PathVariable String notificationId, @RequestParam String userId) {
        var command = new MarkNotificationReadCommand(userId, notificationId);
        var result = markNotificationAsRead.handle(command);
        return result ? ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
