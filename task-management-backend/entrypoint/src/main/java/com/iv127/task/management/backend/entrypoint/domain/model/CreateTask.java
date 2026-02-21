package com.iv127.task.management.backend.entrypoint.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class CreateTask {

    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;

}
