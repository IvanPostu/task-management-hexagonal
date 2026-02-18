package com.iv127.task.management.backend.entrypoint;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"repository-memory"})
@SpringBootTest
public class AppTest {

    @Test
    public void contextLoads() {
    }

}
