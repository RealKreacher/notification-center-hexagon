package com.wandera.hw.notificationcenter.user.infrastructure;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationId;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import com.wandera.hw.notificationcenter.user.infrastructure.exception.NoSuchUserException;
import com.wandera.hw.notificationcenter.user.infrastructure.model.NotificationEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserNotificationInMemoryAdapterTest {

    private UserNotificationInMemoryAdapter repository;

    private NotificationEntity notification1;
    private NotificationEntity notification2;

    private NotificationEntity notification3;

    private final String testId1 = UUID.randomUUID().toString();
    private final String testId2 = UUID.randomUUID().toString();
    private final String testId3 = UUID.randomUUID().toString();

    private final String userID = "testUserID";


    @BeforeEach
    public void init() {
        notification1 = new NotificationEntity(
                testId1,
                userID,
                LocalDateTime.of(2022, 12, 24, 12, 12),
                NotificationType.INFO.name(),
                "Title1",
                "Detail1",
                false);

        notification2 = new NotificationEntity(
                testId2,
                userID,
                LocalDateTime.of(2022, 12, 24, 12, 12),
                NotificationType.BLOCK.name(),
                "Title2",
                "Detail2",
                false);

        notification3 = new NotificationEntity(
                testId3,
                userID,
                LocalDateTime.of(2022, 12, 24, 12, 10),
                NotificationType.BLOCK.name(),
                "Title3",
                "Detail3",
                false);


        List<NotificationEntity> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);

        Map<String, List<NotificationEntity>> notificationMap = new HashMap<>();
        notificationMap.put(userID, notifications);
        repository = new UserNotificationInMemoryAdapter(notificationMap);
    }

    @AfterEach
    void clean() {
        notification1 = null;
        notification2 = null;
        notification3 = null;

        repository = null;
    }

    @Test
    void findUserNotifications_success_size() {
        // WHEN
        var notifications = repository.findUserNotifications(userID);

        // THEN
        assertEquals(3, notifications.size(), "Expected 3 notifications");
    }

    @Test
    void findUserNotifications_success_ordering() {
        // WHEN
        var notifications = repository.findUserNotifications(userID);

        // THEN
        assertEquals(notification3.getNotificationId(), notifications.get(0).notificationIdAsString(),
                "Notifications out of order");
        assertEquals(notification2.getNotificationId(), notifications.get(1).notificationIdAsString(),
                "Notifications out of order");
        assertEquals(notification1.getNotificationId(), notifications.get(2).notificationIdAsString(),
                "Notifications out of order");
    }

    @Test
    void findUserNotifications_invalid_userId() {
        assertThrows(NoSuchUserException.class, () -> repository.findUserNotifications("InvalidUserID"),
                "Expected NoSuchUserException to be thrown");
    }

    @Test
    void findUserNotification_success() {
        // WHEN
        var testNotificationOptional = repository.findUserNotification(userID, testId1);

        // THEN
        assertTrue(testNotificationOptional.isPresent());

        var testNotification = testNotificationOptional.get();
        assertEquals(notification1.toDomainNotification(), testNotification, "Expected notification not found");
    }

    @Test
    void findUserNotification_invalid_userId() {
        assertThrows(NoSuchUserException.class, () -> repository.findUserNotification("InvalidUserID", testId1),
                "Expected NoSuchUserException to be thrown");
    }

    @Test
    void findUserNotification_invalid_notificationId() {
        var userNotification = repository.findUserNotification(userID, "InvalidNotificationID");
        assertTrue(userNotification.isEmpty(), "Expected empty Optional");
    }

    @Test
    void updateNotification() {
        // GIVEN
        var domainNotification = notification1
                .toDomainNotification()
                .markAsRead();

        // WHEN
        var result = repository.updateNotification(domainNotification);

        // THEN
        assertTrue(result, "Expected successful result");

        var optionalNotification = repository.findUserNotification(userID, notification1.getNotificationId());
        assertTrue(optionalNotification.isPresent(), "Expected notification to be present");

        var testNotification = optionalNotification.get();
        assertTrue(testNotification.read(), "Expected notification to be marked as read");
    }

    @Test
    void updateNotification_invalid_userId() {
        var invaildNotification = new Notification(NotificationId.of(notification1.getNotificationId()),
                UserId.of("Invalid userID"),
                notification1.getNotificationDate(),
                NotificationType.BLOCK,
                notification1.getNotificationTitle(),
                notification1.getNotificationDetail(),
                true);

        assertThrows(NoSuchUserException.class, () -> repository.updateNotification(invaildNotification),
                "Expected NoSuchUserException to be thrown");
    }

    @Test
    void updateNotification_invalid_notificationId() {
        System.out.println(UUID.randomUUID());
        // GIVEN
        var domainNotification = new Notification(NotificationId.of("a6e36039-611e-4d4d-80c1-9cd3c653e6b4"),
                UserId.of(notification1.getUserId()),
                notification1.getNotificationDate(),
                NotificationType.BLOCK,
                notification1.getNotificationTitle(),
                notification1.getNotificationDetail(),
                true);

        // WHEN
        var result = repository.updateNotification(domainNotification);

        // THEN
        assertFalse(result, "Expected update to fail");
    }

    @Test
    void deleteNotification_success() {
        // WHEN
        repository.deleteNotification(notification1.getUserId(), notification1.getNotificationId());

        // THEN
        var foundNotification = repository.findUserNotification(notification1.getUserId(), notification1.getNotificationId());
        assertTrue(foundNotification.isEmpty());
    }

    @Test
    void deleteNotification_invalid_userId() {
        assertThrows(NoSuchUserException.class,
                () -> repository.deleteNotification("InvalidUserID", notification1.getNotificationId()),
                "Expected NoSuchUserException to be thrown");
    }

    @Test
    void deleteNotification_invalid_notificationId() {
        var result = repository.deleteNotification(notification1.getUserId(), "invalidNotificationId");
        assertFalse(result, "Expected deletion to fail");
    }
}