package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import com.iv127.task.management.backend.entrypoint.App;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({"repository-mysql"})
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerWithMySqlRepositoryTest {

    @Autowired
    WebApplicationContext context;

    @Test
    public void testCreateTask() {
        TaskGraphqlControllerRepositoryTest.testCreateTask(context);
    }

    @Test
    public void testUpdateTask() {
        TaskGraphqlControllerRepositoryTest.testUpdateTask(context);
    }

    @Test
    public void testGetTasksByFilter() {
        TaskGraphqlControllerRepositoryTest.testGetTasksByFilter(context);
    }

    @Test
    public void testDeleteTask() {
        TaskGraphqlControllerRepositoryTest.testDeleteTask(context);
    }

}
