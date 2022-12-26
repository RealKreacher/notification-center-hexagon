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
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"data.filepath=src/test/resources/example-data.csv"})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yaml")
class UserNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void notifications_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notifications[1].id", is("bb486f1c-bdbc-4abb-8770-29ee51798763")))
                .andExpect(jsonPath("$.notifications[1].type", is("INFO")));
    }

    @Test
    void notifications_invalid_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void notificationDetail_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId", is("bb486f1c-bdbc-4abb-8770-29ee51798763")))
                .andExpect(jsonPath("$.userId", is("user4")))
                .andExpect(jsonPath("$.date", is("2022-09-25T11:02:04")))
                .andExpect(jsonPath("$.type", is("INFO")))
                .andExpect(jsonPath("$.title", is("Test notification 1")))
                .andExpect(jsonPath("$.detail", is("Notification test description 1.")))
                .andExpect(jsonPath("$.read", is(false)));
    }

    @Test
    void notificationDetail_invalid_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user1"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void notificationDetail_no_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void notificationDetail_invalid_notification() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798765")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user4"))
                .andExpect(status().isNotFound());

    }

    @Test
    void markNotificationAsRead_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.read", is(true)));
    }

    @Test
    void markNotificationAsRead_invalid_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void markNotificationAsRead_no_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798763")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void markNotificationAsRead_invalid_notification() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(USER_PREFIX + USER_NOTIFICATIONS_URL + "/bb486f1c-bdbc-4abb-8770-29ee51798765")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user4"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotification_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(USER_PREFIX + USER_NOTIFICATIONS_URL + "/a2cd6365-0bc9-42a0-8161-5316bc535414")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user2"))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(USER_PREFIX + USER_NOTIFICATIONS_URL + "/a2cd6365-0bc9-42a0-8161-5316bc535414")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotification_invalid_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(USER_PREFIX + USER_NOTIFICATIONS_URL + "/a2cd6365-0bc9-42a0-8161-5316bc535414")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteNotification_no_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(USER_PREFIX + USER_NOTIFICATIONS_URL + "/a2cd6365-0bc9-42a0-8161-5316bc535414")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteNotification_invalid_notification() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(USER_PREFIX + USER_NOTIFICATIONS_URL + "/a2cd6365-0bc9-42a0-8161-5316bc535415")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", "user4"))
                .andExpect(status().isNotFound());
    }
}