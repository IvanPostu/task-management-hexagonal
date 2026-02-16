package com.iv127.task.management.backend.entrypoint.domain.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CreateTask {

    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;

}
