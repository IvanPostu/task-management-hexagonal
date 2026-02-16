package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import com.iv127.task.management.backend.entrypoint.domain.model.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class TaskGQLMapper {

    private static final Map<TaskPriorityDto, TaskPriority> TASK_PRIORITY_MAP_BY_TASK_PRIORITY_DTO = new EnumMap<>(Map.of(
            TaskPriorityDto.NONE, TaskPriority.NONE,
            TaskPriorityDto.LOW, TaskPriority.LOW,
            TaskPriorityDto.MEDIUM, TaskPriority.MEDIUM,
            TaskPriorityDto.HIGH, TaskPriority.HIGH
    ));

    private static final Map<TaskPriority, TaskPriorityDto> TASK_PRIORITY_DTO_MAP_BY_TASK_PRIORITY = new EnumMap<>(Map.of(
            TaskPriority.NONE, TaskPriorityDto.NONE,
            TaskPriority.LOW, TaskPriorityDto.LOW,
            TaskPriority.MEDIUM, TaskPriorityDto.MEDIUM,
            TaskPriority.HIGH, TaskPriorityDto.HIGH
    ));

    private static final Map<TaskStatusDto, TaskStatus> TASK_STATUS_MAP_BY_TASK_STATUS_DTO = new EnumMap<>(Map.of(
            TaskStatusDto.CREATED, TaskStatus.CREATED,
            TaskStatusDto.TODO, TaskStatus.TODO,
            TaskStatusDto.IN_PROGRESS, TaskStatus.IN_PROGRESS,
            TaskStatusDto.COMPLETED, TaskStatus.COMPLETED
    ));

    private static final Map<TaskStatus, TaskStatusDto> TASK_STATUS_DTO_MAP_BY_TASK_STATUS = new EnumMap<>(Map.of(
            TaskStatus.CREATED, TaskStatusDto.CREATED,
            TaskStatus.TODO, TaskStatusDto.TODO,
            TaskStatus.IN_PROGRESS, TaskStatusDto.IN_PROGRESS,
            TaskStatus.COMPLETED, TaskStatusDto.COMPLETED
    ));

    public static CreateTask toCreateTask(CreateTaskDto createTaskDto) {
        return CreateTask.builder()
                .title(createTaskDto.title())
                .description(createTaskDto.description())
                .priority(Optional.ofNullable(TASK_PRIORITY_MAP_BY_TASK_PRIORITY_DTO.get(createTaskDto.priority()))
                        .orElse(TaskPriority.NONE))
                .dueDate(createTaskDto.dueDate())
                .build();
    }

    public static TaskDto toTaskDto(Task task) {
        return new TaskDto(task.getId(),
                task.getTitle(),
                task.getDescription(),
                TASK_PRIORITY_DTO_MAP_BY_TASK_PRIORITY.get(task.getPriority()),
                TASK_STATUS_DTO_MAP_BY_TASK_STATUS.get(task.getStatus()),
                task.getDueDate(),
                task.getCreatedDate());
    }

    public static UpdateTask toUpdateTask(UpdateTaskDto updateTaskDto) {
        return UpdateTask
                .builder()
                .id(updateTaskDto.getId())
                .title(updateTaskDto.getTitle())
                .description(updateTaskDto.getDescription())
                .priority(TASK_PRIORITY_MAP_BY_TASK_PRIORITY_DTO.get(updateTaskDto.getPriority()))
                .status(TASK_STATUS_MAP_BY_TASK_STATUS_DTO.get(updateTaskDto.getStatus()))
                .dueDate(updateTaskDto.getDueDate())
                .build();
    }

    public static TaskFilter toTaskFilter(TaskFilterInput filter) {
        if (filter == null) {
            return TaskFilter.builder().build();
        }

        return TaskFilter.builder()
                .priority(TASK_PRIORITY_MAP_BY_TASK_PRIORITY_DTO.get(filter.priority()))
                .status(TASK_STATUS_MAP_BY_TASK_STATUS_DTO.get(filter.status()))
                .dueDateBefore(filter.dueDateBefore())
                .build();
    }
}
