package com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class TaskInMemoryEntity {

    private final String id;
    private final String title;
    private final String description;
    private final String priority;
    private final String status;
    private final LocalDate dueDate;
    private final Instant createdDate;

}
