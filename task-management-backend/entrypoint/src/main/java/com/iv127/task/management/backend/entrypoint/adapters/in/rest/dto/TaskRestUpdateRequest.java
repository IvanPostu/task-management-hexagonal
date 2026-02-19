package com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto;

import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TaskRestUpdateRequest {
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;
}
