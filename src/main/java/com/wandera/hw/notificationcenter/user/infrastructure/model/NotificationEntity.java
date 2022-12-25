package com.wandera.hw.notificationcenter.user.infrastructure.model;

import com.wandera.hw.notificationcenter.user.core.model.Notification;
import com.wandera.hw.notificationcenter.user.core.model.NotificationId;
import com.wandera.hw.notificationcenter.user.core.model.NotificationType;
import com.wandera.hw.notificationcenter.user.core.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class NotificationEntity {

    private String notificationId;
    private String userId;
    private LocalDateTime notificationDate;
    private String notificationType;
    private String notificationTitle;
    private String notificationDetail;
    private boolean read;

    public Notification toDomainNotification() {
        return new Notification(new NotificationId(UUID.fromString(notificationId)),
                new UserId(userId),
                notificationDate,
                NotificationType.valueOf(notificationType),
                notificationTitle,
                notificationDetail,
                read
        );
    }

    // TODO think about this one
    /**
     * Generic method to update any field.
     * Maybe this is overkill and bad idea since it kinda couple this entity
     * with the domain model as Fields are specified in specific user cases.
     * @param newValue
     * @param fieldToUpdate
     * @return
     */
    public boolean update(Object newValue, String fieldToUpdate) {
        try {
            Field field = NotificationEntity.class.getDeclaredField(fieldToUpdate);
            field.setAccessible(true);
            field.set(this, newValue);

            return true;
        } catch (NoSuchFieldException e) {
            log.error("Trying to update non existing field", e);
            return false;
        } catch (IllegalAccessException e) {
            log.error("Updating failed due illegal access to field", e);
            return false;
        }
    }
}
