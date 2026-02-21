package com.iv127.task.management.backend.entrypoint.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TaskFilter {

    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDateBefore;

}
