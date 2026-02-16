package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.UUID;

@Controller
public class TaskMutationController {

    @MutationMapping
    public TaskDto createTask(@Argument CreateTaskDto input) {

        return new TaskDto(
                UUID.randomUUID().toString(),
                input.title(),
                input.description(),
                input.priority(),
                TaskStatusDto.CREATED,
                input.dueDate(),
                Instant.now()
        );
    }
}
