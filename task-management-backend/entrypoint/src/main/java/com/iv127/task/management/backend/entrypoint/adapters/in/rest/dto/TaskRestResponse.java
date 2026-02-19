package com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto;

import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class TaskRestResponse {

    private String id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private Instant createDate;

}
