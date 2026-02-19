package com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto;

import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TaskRestCreateRequest {
    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;
}
