package com.wandera.hw.notificationcenter.user.infrastructure.config;

import com.wandera.hw.notificationcenter.user.core.port.DeleteNotificationFacade;
import com.wandera.hw.notificationcenter.user.core.port.GetUserNotificationDetailFacade;
import com.wandera.hw.notificationcenter.user.core.port.GetUserNotificationFacade;
import com.wandera.hw.notificationcenter.user.core.port.MarkNotificationAsReadFacade;
import com.wandera.hw.notificationcenter.user.core.port.incoming.DeleteNotification;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotificationDetail;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetNotifications;
import com.wandera.hw.notificationcenter.user.core.port.incoming.MarkNotificationRead;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.NotificationCSVLoader;
import com.wandera.hw.notificationcenter.user.infrastructure.UserNotificationInMemoryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Value("${data.filepath}")
    private String path;

    @Bean
    public GetNotifications getUserNotifications(UserNotificationRepository repository) {
        return new GetUserNotificationFacade(repository);
    }

    @Bean
    public GetNotificationDetail getUserNotificationDetail(UserNotificationRepository repository) {
        return new GetUserNotificationDetailFacade(repository);
    }

    @Bean
    public MarkNotificationRead markNotificationAsRead(UserNotificationRepository repository) {
        return new MarkNotificationAsReadFacade(repository);
    }

    @Bean
    public DeleteNotification deleteNotification(UserNotificationRepository repository) {
        return new DeleteNotificationFacade(repository);
    }

    @Bean
    public UserNotificationRepository userNotificationRepository(NotificationCSVLoader csvLoader) {
        var notifications = csvLoader.loadNotifications(path);
        return new UserNotificationInMemoryAdapter(notifications);
    }
}
