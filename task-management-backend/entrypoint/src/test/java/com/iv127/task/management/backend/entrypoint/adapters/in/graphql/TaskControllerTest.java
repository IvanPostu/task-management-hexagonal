package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import com.iv127.task.management.backend.entrypoint.App;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = App.class)
public class TaskControllerTest {

    @Autowired
    WebApplicationContext context;

    @Test
    public void testCreateTask() {
        String query = """
                mutation CreateTask($input: CreateTaskDto!)
                {
                    createTask(input: $input) { id title description priority status dueDate createDate }
                }
                """;

        WebTestClient.Builder webTestClientBuilder = MockMvcWebTestClient.bindToApplicationContext(context)
                .configureClient()
                .baseUrl("/graphql");

        HttpGraphQlTester tester = HttpGraphQlTester
                .builder(webTestClientBuilder)
                .build();

        Instant beforeCreatedDate = Instant.now();
        TaskDto taskDto = tester.document(query)
                .variable("input", new CreateTaskDto("title1", "description1", TaskPriorityDto.LOW, LocalDate.parse("2026-12-25")))
                .execute()
                .path("createTask")
                .entity(TaskDto.class)
                .get();

        assertThat(taskDto.id()).isNotEmpty();
        assertThat(taskDto.title()).isEqualTo("title1");
        assertThat(taskDto.description()).isEqualTo("description1");
        assertThat(taskDto.priority()).isEqualTo(TaskPriorityDto.LOW);
        assertThat(taskDto.status()).isEqualTo(TaskStatusDto.CREATED);
        assertThat(taskDto.dueDate()).isEqualTo(LocalDate.parse("2026-12-25"));
        assertThat(taskDto.createDate())
                .isAfter(beforeCreatedDate)
                .isBefore(Instant.now());

    }

    @Test
    public void testUpdateTask() {
        String createTaskMutation = """
                mutation CreateTask($input: CreateTaskDto!)
                {
                    createTask(input: $input) { id title description priority status dueDate createDate }
                }
                """;
        String updateTaskMutation = """
                mutation UpdateTask($input: UpdateTaskDto!) {
                    updateTask(input: $input) { id title description priority status dueDate createDate }
                }
                """;

        WebTestClient.Builder webTestClientBuilder = MockMvcWebTestClient.bindToApplicationContext(context)
                .configureClient()
                .baseUrl("/graphql");

        HttpGraphQlTester tester = HttpGraphQlTester
                .builder(webTestClientBuilder)
                .build();

        TaskDto createdTask = tester.document(createTaskMutation)
                .variable("input", new CreateTaskDto("title1", "description1", TaskPriorityDto.LOW, LocalDate.parse("2026-12-25")))
                .execute()
                .path("createTask")
                .entity(TaskDto.class)
                .get();

        TaskDto updatedTask = tester.document(updateTaskMutation)
                .variable("input", new UpdateTaskDto(createdTask.id(), "title2", "description2", TaskPriorityDto.HIGH, TaskStatusDto.IN_PROGRESS, LocalDate.parse("2027-12-25")))
                .execute()
                .path("updateTask")
                .entity(TaskDto.class)
                .get();

        assertThat(updatedTask.id()).isEqualTo(createdTask.id());
        assertThat(updatedTask.title()).isEqualTo("title2");
        assertThat(updatedTask.description()).isEqualTo("description2");
        assertThat(updatedTask.priority()).isEqualTo(TaskPriorityDto.HIGH);
        assertThat(updatedTask.status()).isEqualTo(TaskStatusDto.IN_PROGRESS);
        assertThat(updatedTask.dueDate()).isEqualTo(LocalDate.parse("2027-12-25"));
        assertThat(updatedTask.createDate()).isEqualTo(createdTask.createDate());
    }

    @Test
    public void testGetTasksByFilter() {
        String createTaskMutation = """
                mutation CreateTask($input: CreateTaskDto!)
                {
                    createTask(input: $input) { id title description priority status dueDate createDate }
                }
                """;
        String getTasksQuery = """
                query TaskFilterInput($filter: TaskFilterInput!) {
                    getTasksByFilter(filter: $filter) { id title description priority status dueDate createDate }
                }
                """;

        WebTestClient.Builder webTestClientBuilder = MockMvcWebTestClient.bindToApplicationContext(context)
                .configureClient()
                .baseUrl("/graphql");

        HttpGraphQlTester tester = HttpGraphQlTester
                .builder(webTestClientBuilder)
                .build();

        String title = RandomStringUtils.insecure().nextAlphanumeric(32);
        String description = RandomStringUtils.insecure().nextAlphanumeric(32);

        TaskDto createdTask = tester.document(createTaskMutation)
                .variable("input", new CreateTaskDto(title, description, TaskPriorityDto.LOW, LocalDate.parse("2026-12-25")))
                .execute()
                .path("createTask")
                .entity(TaskDto.class)
                .get();

        List<TaskDto> tasks = tester.document(getTasksQuery)
                .variable("filter", new TaskFilterInput(TaskPriorityDto.LOW, TaskStatusDto.CREATED, LocalDate.parse("2026-12-26")))
                .execute()
                .path("getTasksByFilter")
                .entityList(TaskDto.class)
                .get();

        assertThat(tasks)
                .anySatisfy(taskDto -> {
                    assertThat(taskDto)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });
    }

    @Test
    public void testDeleteTask() {
        String createTaskMutation = """
                mutation CreateTask($input: CreateTaskDto!)
                {
                    createTask(input: $input) { id title description priority status dueDate createDate }
                }
                """;
        String getTasksQuery = """
                query TaskFilterInput($filter: TaskFilterInput!) {
                    getTasksByFilter(filter: $filter) { id title description priority status dueDate createDate }
                }
                """;
        String deleteTask = """
                mutation DeleteTask($id: String!) { deleteTaskById(id: $id) }
                """;

        WebTestClient.Builder webTestClientBuilder = MockMvcWebTestClient.bindToApplicationContext(context)
                .configureClient()
                .baseUrl("/graphql");

        HttpGraphQlTester tester = HttpGraphQlTester
                .builder(webTestClientBuilder)
                .build();

        String title = RandomStringUtils.insecure().nextAlphanumeric(32);
        String description = RandomStringUtils.insecure().nextAlphanumeric(32);

        TaskDto createdTask = tester.document(createTaskMutation)
                .variable("input", new CreateTaskDto(title, description, TaskPriorityDto.LOW, LocalDate.parse("2026-12-25")))
                .execute()
                .path("createTask")
                .entity(TaskDto.class)
                .get();

        List<TaskDto> tasks = tester.document(getTasksQuery)
                .variable("filter", new TaskFilterInput(TaskPriorityDto.LOW, TaskStatusDto.CREATED, LocalDate.parse("2026-12-26")))
                .execute()
                .path("getTasksByFilter")
                .entityList(TaskDto.class)
                .get();

        assertThat(tasks)
                .anySatisfy(taskDto -> {
                    assertThat(taskDto)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });

        Boolean deleted = tester.document(deleteTask)
                .variable("id", createdTask.id())
                .execute()
                .path("deleteTaskById")
                .entity(Boolean.class)
                .get();
        assertThat(deleted).isTrue();

        tasks = tester.document(getTasksQuery)
                .variable("filter", new TaskFilterInput(TaskPriorityDto.LOW, TaskStatusDto.CREATED, LocalDate.parse("2026-12-26")))
                .execute()
                .path("getTasksByFilter")
                .entityList(TaskDto.class)
                .get();

        assertThat(tasks)
                .noneSatisfy(taskDto -> {
                    assertThat(taskDto)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });
    }

}
