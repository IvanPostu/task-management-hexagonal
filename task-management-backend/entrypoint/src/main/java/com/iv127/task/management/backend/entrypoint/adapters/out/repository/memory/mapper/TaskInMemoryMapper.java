package com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.mapper;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.entity.TaskInMemoryEntity;
import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;

import java.util.Optional;

public final class TaskInMemoryMapper {
    private TaskInMemoryMapper() {
    }

    public static Task toDomain(TaskInMemoryEntity entity) {
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

    public static TaskInMemoryEntity toEntity(CreateTask createTask) {
        return TaskInMemoryEntity.builder()
                .title(createTask.getTitle())
                .description(createTask.getDescription())
                .priority(Optional.ofNullable(createTask.getPriority())
                        .map(Enum::name)
                        .orElse(TaskPriority.NONE.name()))
                .status(TaskStatus.CREATED.name())
                .dueDate(createTask.getDueDate())
                .build();
    }

}
