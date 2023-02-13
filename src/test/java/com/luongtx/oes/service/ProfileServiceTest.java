package com.luongtx.oes.service;

import com.luongtx.oes.config.TestConfig;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
@Log4j2
class ProfileServiceTest {

    @Autowired
    ProfileService profileService;

    @Test
    void getCurrentUserProfile() {
        profileService.getCurrentUserProfile("1234567");
//        long startTime = System.currentTimeMillis();
//        try {
//            profileService.getCurrentUserProfile("1234566");
//        }catch (Exception e) {
//           log.error(e.getMessage());
//        }
//        long elapsedTime = System.currentTimeMillis() - startTime;
//        System.out.printf("Execution time: %s (ms)", elapsedTime);
    }
}