package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import java.time.Instant;
import java.time.LocalDate;

public record TaskDto(
        String id,
        String title,
        String description,
        TaskPriorityDto priority,
        TaskStatusDto status,
        LocalDate dueDate,
        Instant createDate
) {
}
