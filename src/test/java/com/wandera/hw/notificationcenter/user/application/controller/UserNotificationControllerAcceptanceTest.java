package com.wandera.hw.notificationcenter.user.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.wandera.hw.notificationcenter.user.application.controller.URLConstants.USER_NOTIFICATIONS_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserNotificationControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void notifications() throws Exception {
        mockMvc.perform(get(USER_NOTIFICATIONS_URL)
                        .param("userId", "user1"))
                .andExpect(status().isOk());
    }

    @Test
    void notificationDetail() {
    }

    @Test
    void markNotificationAsRead() {
    }

    @Test
    void deleteNotification() {
    }
}