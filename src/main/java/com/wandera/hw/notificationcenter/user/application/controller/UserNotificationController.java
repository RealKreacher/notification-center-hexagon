package com.wandera.hw.notificationcenter.user.application.controller;

import com.wandera.hw.notificationcenter.user.application.model.UserNotificationDetail;
import com.wandera.hw.notificationcenter.user.application.model.UserNotifications;
import com.wandera.hw.notificationcenter.user.core.model.DeleteNotificationCommand;
import com.wandera.hw.notificationcenter.user.core.model.MarkNotificationReadCommand;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationDetailQuery;
import com.wandera.hw.notificationcenter.user.core.model.UserNotificationsQuery;
import com.wandera.hw.notificationcenter.user.core.port.incoming.DeleteNotification;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotificationDetail;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotifications;
import com.wandera.hw.notificationcenter.user.core.port.incoming.MarkNotificationRead;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.ErrorResponseBody;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchNotificationException;
import com.wandera.hw.notificationcenter.user.infrastructure.logging.LogAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static com.wandera.hw.notificationcenter.user.application.controller.URLConstants.USER_NOTIFICATIONS_URL;
import static com.wandera.hw.notificationcenter.user.application.controller.URLConstants.USER_NOTIFICATION_URL;
import static com.wandera.hw.notificationcenter.user.application.controller.URLConstants.USER_PREFIX;
import static com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchNotificationException.NOTIFICATION_NOT_FOUND;
import static com.wandera.hw.notificationcenter.user.infrastructure.logging.LoggingAOP.DEBUG;
import static com.wandera.hw.notificationcenter.user.infrastructure.logging.LoggingAOP.INFO;

@Tag(name = "User", description = "User notifications api")
@RestController
@RequestMapping(USER_PREFIX)
@RequiredArgsConstructor
public class UserNotificationController {

    private final GetNotifications getUserNotifications;
    private final GetNotificationDetail getUserNotificationDetail;
    private final MarkNotificationRead markNotificationAsRead;
    private final DeleteNotification deleteNotification;

    @Operation(summary = "Get notifications",
            description = "Get list of notifications for given user. Notifications are sorted by date and grouped by type",
            tags = "User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users notifications",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotifications.class))}),
            @ApiResponse(responseCode = "400", description = "No such user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    @GetMapping(USER_NOTIFICATIONS_URL)
    @LogAccess(level = INFO)
    public ResponseEntity<UserNotifications> notifications(@RequestParam String userId) {
        var query = new UserNotificationsQuery(UserId.of(userId));
        var notifications = getUserNotifications.handle(query);
        return ResponseEntity.of(UserNotifications.of(notifications));
    }

    @Operation(summary = "Get notification",
            description = "Get specific notification for given user. The notification contains all details available",
            tags = "User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the notification",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserNotificationDetail.class))}),
            @ApiResponse(responseCode = "400", description = "No such user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))}),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    @GetMapping(USER_NOTIFICATION_URL)
    @LogAccess(level = DEBUG)
    public ResponseEntity<UserNotificationDetail> notificationDetail(@PathVariable String notificationId,
                                                                     @RequestParam String userId) {
        var query = UserNotificationDetailQuery.builder()
                .userId(userId)
                .notificationId(notificationId)
                .build();

        var notificationDetail = getUserNotificationDetail.handle(query);

        return notificationDetail.map(UserNotificationDetail::of)
                .map(ResponseEntity::of)
                .orElseThrow(() -> new NoSuchNotificationException(NOTIFICATION_NOT_FOUND));
    }

    @Operation(summary = "Mark notification as read",
            tags = "User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification marked as read"),
            @ApiResponse(responseCode = "400", description = "No such user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))}),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    @PatchMapping(USER_NOTIFICATION_URL)
    public ResponseEntity<HttpStatus> markNotificationAsRead(@PathVariable String notificationId, @RequestParam String userId) {
        var command = new MarkNotificationReadCommand(userId, notificationId);
        var result = markNotificationAsRead.handle(command);

        if (result) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        throw new NoSuchNotificationException(NOTIFICATION_NOT_FOUND);
    }

    @Operation(summary = "Delete notification",
            tags = "User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification deleted"),
            @ApiResponse(responseCode = "400", description = "No such user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))}),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class))})
    })
    @DeleteMapping(USER_NOTIFICATION_URL)
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable String notificationId, @RequestParam String userId) {
        var command = new DeleteNotificationCommand(userId, notificationId);

        var result = deleteNotification.handle(command);

        if (result) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        throw new NoSuchNotificationException(NOTIFICATION_NOT_FOUND);
    }
}
