package com.wandera.hw.notificationcenter.user.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "User notifications api")
@RestController("user")
@RequiredArgsConstructor
public class UserNotificationController {
    // TODO: inject domain adapter
    // maybe rename to rest controller?

}
