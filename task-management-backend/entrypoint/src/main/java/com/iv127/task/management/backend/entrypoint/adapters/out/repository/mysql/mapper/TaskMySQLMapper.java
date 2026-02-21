package com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.mapper;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.entity.TaskMySQLEntity;
import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;

import java.util.Optional;

public class TaskMySQLMapper {

    public static Task toDomain(TaskMySQLEntity entity) {
        return Task.builder()
                .id(String.valueOf(entity.getId()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .priority(Optional.ofNullable(entity.getPriority()).map(TaskPriority::valueOf).orElse(null))
                .status(Optional.ofNullable(entity.getStatus()).map(TaskStatus::valueOf).orElse(null))
                .dueDate(entity.getDueDate())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public static TaskMySQLEntity toEntity(CreateTask createTask) {
        String priority = Optional.ofNullable(createTask.getPriority())
                .map(Enum::name)
                .orElse(TaskPriority.NONE.name());
        return new TaskMySQLEntity(null, createTask.getTitle(), createTask.getDescription(), priority, TaskStatus.CREATED.name(), createTask.getDueDate(), null);
    }

}
