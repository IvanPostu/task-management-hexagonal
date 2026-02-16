package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import java.time.LocalDate;

public record TaskFilterInput(TaskPriorityDto priority, TaskStatusDto status, LocalDate dueDateBefore) {
}
