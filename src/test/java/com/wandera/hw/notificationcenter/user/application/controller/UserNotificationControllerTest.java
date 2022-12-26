package com.wandera.hw.notificationcenter.user.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.wandera.hw.notificationcenter.user.application.controller.URLConstants.USER_NOTIFICATIONS_URL;
import static com.wandera.hw.notificationcenter.user.application.controller.URLConstants.USER_PREFIX;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"data.filepath=src/test/resources/example-data.csv"})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yaml")
class UserNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void notifications() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user4"))
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