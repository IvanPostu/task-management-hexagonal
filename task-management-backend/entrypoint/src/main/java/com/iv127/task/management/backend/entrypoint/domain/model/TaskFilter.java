package com.iv127.task.management.backend.entrypoint.domain.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskFilter {

    private String id;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDateBefore;

}
