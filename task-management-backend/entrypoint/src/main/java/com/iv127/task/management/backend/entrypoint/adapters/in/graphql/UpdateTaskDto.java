package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Builder
@Data
public class UpdateTaskDto {

    private final String id;

    private final String title;
    private final String description;
    private final TaskPriorityDto priority;
    private final TaskStatusDto status;
    private final LocalDate dueDate;

}
