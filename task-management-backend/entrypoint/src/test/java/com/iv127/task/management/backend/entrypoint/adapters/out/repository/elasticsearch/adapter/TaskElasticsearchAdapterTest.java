package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.adapter;

import com.iv127.task.management.backend.entrypoint.App;
import com.iv127.task.management.backend.entrypoint.config.ElasticsearchTestConfig;
import com.iv127.task.management.backend.entrypoint.domain.model.*;
import com.iv127.task.management.backend.entrypoint.domain.port.out.TaskDBPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"repository-elasticsearch"})
@SpringBootTest(classes = App.class)
@Import(ElasticsearchTestConfig.class)
class TaskElasticsearchAdapterTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TaskDBPort taskDBPort;

    @Test
    public void testTaskDbPortType() {
        assertThat(taskDBPort).isExactlyInstanceOf(TaskESMemoryStorageAdapter.class);
    }

    @Test
    public void testCreateAndGet() {
        Task createdTask = taskDBPort.saveTask(new CreateTask("title1", "description1", TaskPriority.HIGH, LocalDate.parse("2026-12-26")));
        List<Task> tasks = taskDBPort.getTasksByFilter(new TaskFilter(null, null, null));

        assertThat(tasks)
                .anySatisfy(task -> {
                    assertThat(task)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                    assertThat(task.getId()).isNotEmpty();
                    assertThat(task.getTitle()).isEqualTo("title1");
                    assertThat(task.getDescription()).isEqualTo("description1");
                    assertThat(task.getPriority()).isEqualTo(TaskPriority.HIGH);
                    assertThat(task.getDueDate()).isEqualTo(LocalDate.parse("2026-12-26"));
                    assertThat(task.getStatus()).isEqualTo(TaskStatus.CREATED);
                });
    }

    @Test
    public void testUpdateAndGet() {
        Task createdTask = taskDBPort.saveTask(new CreateTask("title1", "description1",
                TaskPriority.HIGH, LocalDate.parse("2026-12-26")));
        taskDBPort.updateTask(new UpdateTask(createdTask.getId(), "title2", "description2",
                TaskPriority.LOW, TaskStatus.IN_PROGRESS, LocalDate.parse("2029-12-26")));

        List<Task> tasks = taskDBPort.getTasksByFilter(new TaskFilter(null, null, null));

        assertThat(tasks)
                .anySatisfy(task -> {
                    assertThat(task.getId()).isNotEmpty();
                    assertThat(task.getTitle()).isEqualTo("title2");
                    assertThat(task.getDescription()).isEqualTo("description2");
                    assertThat(task.getPriority()).isEqualTo(TaskPriority.LOW);
                    assertThat(task.getDueDate()).isEqualTo(LocalDate.parse("2029-12-26"));
                    assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
                });
    }

    @Test
    public void testDelete() {
        Task createdTask = taskDBPort.saveTask(new CreateTask("title1", "description1", TaskPriority.HIGH, LocalDate.parse("2026-12-26")));

        List<Task> tasks = taskDBPort.getTasksByFilter(new TaskFilter(null, null, null));
        assertThat(tasks)
                .anySatisfy(task -> {
                    assertThat(task)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                    assertThat(task.getId()).isNotEmpty();
                    assertThat(task.getTitle()).isEqualTo("title1");
                    assertThat(task.getDescription()).isEqualTo("description1");
                    assertThat(task.getPriority()).isEqualTo(TaskPriority.HIGH);
                    assertThat(task.getDueDate()).isEqualTo(LocalDate.parse("2026-12-26"));
                    assertThat(task.getStatus()).isEqualTo(TaskStatus.CREATED);
                });

        assertThat(taskDBPort.deleteTaskById(createdTask.getId()))
                .isTrue();

        tasks = taskDBPort.getTasksByFilter(new TaskFilter(null, null, null));
        assertThat(tasks)
                .noneSatisfy(task -> {
                    assertThat(task)
                            .usingRecursiveComparison()
                            .isEqualTo(createdTask);
                });
    }

}
