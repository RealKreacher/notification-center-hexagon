package com.wandera.hw.notificationcenter.user.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wandera.hw.notificationcenter.user.core.port.GetUserNotificationFacade;
import com.wandera.hw.notificationcenter.user.core.port.incoming.GetUserNotifications;
import com.wandera.hw.notificationcenter.user.core.port.outgoing.UserNotificationRepository;
import com.wandera.hw.notificationcenter.user.infrastructure.NotificationCSVLoader;
import com.wandera.hw.notificationcenter.user.infrastructure.UserNotificationInMemoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class NotificationConfig {

    @Bean
    public GetUserNotifications getUserNotifications(UserNotificationRepository repository) {
        return new GetUserNotificationFacade(repository);
    }

    @Bean
    public UserNotificationRepository userNotificationRepository(NotificationCSVLoader csvLoader) {
       return new UserNotificationInMemoryAdapter(csvLoader);
    }
}
