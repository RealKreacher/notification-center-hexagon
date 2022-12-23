package com.wandera.hw.notificationcenter.user.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

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
        return new UserNotificationInMemoryAdapter(csvLoader);
    }
}
