package com.wandera.hw.notificationcenter.user.infrastructure.config;

import com.wandera.hw.notificationcenter.user.core.port.GetUserNotificationDetailFacade;
import com.wandera.hw.notificationcenter.user.core.port.GetUserNotificationFacade;
import com.wandera.hw.notificationcenter.user.core.port.MarkNotificationAsReadFacade;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotificationDetail;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotifications;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.NotificationCSVLoader;
import com.wandera.hw.notificationcenter.user.infrastructure.UserNotificationInMemoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    public GetUserNotifications getUserNotifications(UserNotificationRepository repository) {
        return new GetUserNotificationFacade(repository);
    }

    @Bean
    public GetUserNotificationDetail getUserNotificationDetail(UserNotificationRepository repository) {
        return new GetUserNotificationDetailFacade(repository);
    }

    @Bean
    public MarkNotificationAsReadFacade markNotificationAsRead(UserNotificationRepository repository) {
        return new MarkNotificationAsReadFacade(repository);
    }

    @Bean
    public UserNotificationRepository userNotificationRepository(NotificationCSVLoader csvLoader) {
        return new UserNotificationInMemoryAdapter(csvLoader);
    }
}
