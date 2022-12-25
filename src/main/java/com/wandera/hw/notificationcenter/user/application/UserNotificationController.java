package com.wandera.hw.notificationcenter.user.application;

import com.wandera.hw.notificationcenter.user.application.model.UserNotificationDetailResponse;
import com.wandera.hw.notificationcenter.user.application.model.UserNotificationsResponse;
import com.wandera.hw.notificationcenter.user.core.model.DeleteNotificationCommand;
import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationDetailQuery;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;
import com.wandera.hw.notificationcenter.user.core.port.incoming.DeleteNotification;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotificationDetail;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotifications;
import com.wandera.hw.notificationcenter.user.core.port.incoming.MarkNotificationRead;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchNotificationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final GetNotifications getUserNotifications;
    private final GetNotificationDetail getUserNotificationDetail;
    private final MarkNotificationRead markNotificationAsRead;

    private final DeleteNotification deleteNotification;

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

        return notificationDetail.map(UserNotificationDetailResponse::of)
                .map(ResponseEntity::of)
                .orElseThrow(() -> new NoSuchNotificationException("Notification not found"));
    }

    @PatchMapping("/notifications/{notificationId}")
    public ResponseEntity<HttpStatus> markNotificationAsRead(@PathVariable String notificationId, @RequestParam String userId) {
        var command = new MarkNotificationReadCommand(userId, notificationId);
        var result = markNotificationAsRead.handle(command);

        if (result) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        throw new NoSuchNotificationException("Notification not found");
    }

    @DeleteMapping("/notifications/{notificationId}")
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable String notificationId, @RequestParam String userId) {
        var command = new DeleteNotificationCommand(userId, notificationId);

        var result = deleteNotification.handle(command);

        if (result) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        throw new NoSuchNotificationException("Notification not found");
    }
}
