package com.iv127.task.management.backend.entrypoint.adapters.in.rest;

import com.iv127.task.management.backend.entrypoint.App;
import com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto.TaskRestCreateRequest;
import com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto.TaskRestResponse;
import com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto.TaskRestUpdateRequest;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskRestControllerRepositoryTest {

    private static final String PROFILE_TEST_IDENTIFIER = "PROFILE_TEST_IDENTIFIER";
    private static final List<String> REPOSITORY_PROFILES = List.of(
            "repository-memory",
            "repository-elasticsearch",
            "repository-mysql"
    );
    private static List<ConfigurableWebApplicationContext> contextList;

    @BeforeAll
    static void initAll() {
        contextList = REPOSITORY_PROFILES.stream()
                .map(profile -> (ConfigurableWebApplicationContext)
                        new SpringApplicationBuilder(App.class)
                                .profiles(profile)
                                .properties("server.port=0") // <-- random port
                                .web(WebApplicationType.SERVLET)
                                .initializers(ctx -> {
                                    ctx.getBeanFactory().registerSingleton(PROFILE_TEST_IDENTIFIER, profile);
                                })
                                .run())
                .toList();
    }

    @AfterAll
    static void tearDownAll() {
        contextList.forEach(ConfigurableApplicationContext::close);
        contextList = null;
    }


    @TestFactory
    public DynamicTest[] testCreateTask() {
        return contextList.stream().map(context -> {
            String profile = context.getBean(PROFILE_TEST_IDENTIFIER, String.class);
            return DynamicTest.dynamicTest("testCreateTask:" + profile, () -> {
                internalTestCreateTask(context);
            });
        }).toArray(DynamicTest[]::new);

    }

    @TestFactory
    public DynamicTest[] testUpdateTask() {
        return contextList.stream().map(context -> {
            String profile = context.getBean(PROFILE_TEST_IDENTIFIER, String.class);
            return DynamicTest.dynamicTest("testUpdateTask:" + profile, () -> {
                internalTestUpdateTask(context);
            });
        }).toArray(DynamicTest[]::new);
    }

    @TestFactory
    public DynamicTest[] testGetTasksByFilter() {
        return contextList.stream().map(context -> {
            String profile = context.getBean(PROFILE_TEST_IDENTIFIER, String.class);
            return DynamicTest.dynamicTest("testGetTasksByFilter:" + profile, () -> {
                internalTestGetTasksByFilter(context);
            });
        }).toArray(DynamicTest[]::new);
    }

    @TestFactory
    public DynamicTest[] testDeleteTask() {
        return contextList.stream().map(context -> {
            String profile = context.getBean(PROFILE_TEST_IDENTIFIER, String.class);
            return DynamicTest.dynamicTest("testDeleteTask:" + profile, () -> {
                internalTestDeleteTask(context);
            });
        }).toArray(DynamicTest[]::new);
    }


    public void internalTestCreateTask(WebApplicationContext context) {
        WebTestClient client = setupWebTestClient(context);

        String title = RandomStringUtils.insecure().nextAlphanumeric(32);
        String description = RandomStringUtils.insecure().nextAlphanumeric(32);
        TaskRestCreateRequest createRequest =
                new TaskRestCreateRequest(title, description, TaskPriority.LOW, LocalDate.parse("2026-12-25"));

        Instant beforeCreatedDate = Instant.now();
        TaskRestResponse createdTask = client.post()
                .uri("/api/tasks")
                .bodyValue(createRequest)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(TaskRestResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isNotEmpty();
        assertThat(createdTask.getTitle()).isEqualTo(title);
        assertThat(createdTask.getDescription()).isEqualTo(description);
        assertThat(createdTask.getPriority()).isEqualTo(TaskPriority.LOW);
        assertThat(createdTask.getStatus()).isEqualTo(TaskStatus.CREATED);
        assertThat(createdTask.getDueDate()).isEqualTo(LocalDate.parse("2026-12-25"));
        assertThat(createdTask.getCreateDate())
                .isAfter(beforeCreatedDate)
                .isBefore(Instant.now());

    }

    public void internalTestUpdateTask(WebApplicationContext context) {
        WebTestClient client = setupWebTestClient(context);

        String title = RandomStringUtils.insecure().nextAlphanumeric(32);
        String description = RandomStringUtils.insecure().nextAlphanumeric(32);
        TaskRestCreateRequest createRequest =
                new TaskRestCreateRequest(title, description, TaskPriority.LOW, LocalDate.parse("2026-12-25"));

        TaskRestResponse createdTask = client.post()
                .uri("/api/tasks")
                .bodyValue(createRequest)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(TaskRestResponse.class)
                .returnResult()
                .getResponseBody();
        assertThat(createdTask).isNotNull();

        TaskRestResponse updatedTask = client.put()
                .uri("/api/tasks/" + createdTask.getId())
                .bodyValue(new TaskRestUpdateRequest("title2", "description2", TaskPriority.HIGH, TaskStatus.IN_PROGRESS, LocalDate.parse("2027-12-25")))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(TaskRestResponse.class)
                .returnResult()
                .getResponseBody();
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getId()).isEqualTo(createdTask.getId());
        assertThat(updatedTask.getTitle()).isEqualTo("title2");
        assertThat(updatedTask.getDescription()).isEqualTo("description2");
        assertThat(updatedTask.getPriority()).isEqualTo(TaskPriority.HIGH);
        assertThat(updatedTask.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(updatedTask.getDueDate()).isEqualTo(LocalDate.parse("2027-12-25"));
        assertThat(updatedTask.getCreateDate()).isEqualTo(createdTask.getCreateDate());
    }

    public void internalTestGetTasksByFilter(WebApplicationContext context) {
        WebTestClient client = setupWebTestClient(context);

        String title = RandomStringUtils.insecure().nextAlphanumeric(32);
        String description = RandomStringUtils.insecure().nextAlphanumeric(32);
        TaskRestCreateRequest createRequest =
                new TaskRestCreateRequest(title, description, TaskPriority.LOW, LocalDate.parse("2026-12-25"));

        TaskRestResponse createdTask = client.post()
                .uri("/api/tasks")
                .bodyValue(createRequest)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(TaskRestResponse.class)
                .returnResult()
                .getResponseBody();
        assertThat(createdTask).isNotNull();

        List<TaskRestResponse> tasks = client.get()
                .uri("/api/tasks")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(new ParameterizedTypeReference<List<TaskRestResponse>>() {
                })
                .returnResult()
                .getResponseBody();
        assertThat(createdTask).isNotNull();

        assertThat(tasks)
                .anySatisfy(taskDto -> {
                    assertThat(taskDto)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });
    }

    public void internalTestDeleteTask(WebApplicationContext context) {
        WebTestClient client = setupWebTestClient(context);

        String title = RandomStringUtils.insecure().nextAlphanumeric(32);
        String description = RandomStringUtils.insecure().nextAlphanumeric(32);
        TaskRestCreateRequest createRequest =
                new TaskRestCreateRequest(title, description, TaskPriority.LOW, LocalDate.parse("2026-12-25"));

        TaskRestResponse createdTask = client.post()
                .uri("/api/tasks")
                .bodyValue(createRequest)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(TaskRestResponse.class)
                .returnResult()
                .getResponseBody();
        assertThat(createdTask).isNotNull();

        List<TaskRestResponse> tasks = client.get()
                .uri("/api/tasks")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(new ParameterizedTypeReference<List<TaskRestResponse>>() {
                })
                .returnResult()
                .getResponseBody();
        assertThat(createdTask).isNotNull();

        assertThat(tasks)
                .anySatisfy(taskDto -> {
                    assertThat(taskDto)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });

        Boolean deleted = client.delete()
                .uri("/api/tasks/" + createdTask.getId())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(new ParameterizedTypeReference<Boolean>() {
                })
                .returnResult()
                .getResponseBody();
        assertThat(deleted).isTrue();

        tasks = client.get()
                .uri("/api/tasks")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(new ParameterizedTypeReference<List<TaskRestResponse>>() {
                })
                .returnResult()
                .getResponseBody();
        assertThat(createdTask).isNotNull();

        assertThat(tasks)
                .noneSatisfy(taskDto -> {
                    assertThat(taskDto)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });
    }

    private static WebTestClient setupWebTestClient(WebApplicationContext context) {
        return WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + getPort(context))
                .build();
    }

    private static int getPort(WebApplicationContext context) {
        ServletWebServerApplicationContext ctx =
                (ServletWebServerApplicationContext) context;
        return Objects.requireNonNull(ctx.getWebServer()).getPort();
    }

}
