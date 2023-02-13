package com.luongtx.oes.service;

import com.luongtx.oes.config.TestConfig;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
@Log4j2
class CatalogServiceTest {

    @Autowired
    CatalogService catalogService;

    @Test
    void findAll() {
        catalogService.findAll();
//        long startTime = System.currentTimeMillis();
//        try {
//            catalogService.findAll();
//        }catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        long elapsedTime = System.currentTimeMillis() - startTime;
//        System.out.printf("Execution time: %s (ms)", elapsedTime);
    }
}