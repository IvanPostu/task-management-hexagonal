package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;


import java.time.LocalDate;

public record CreateTaskDto(
        String title,
        String description,
        TaskPriorityDto priority,
        LocalDate dueDate
) {}
