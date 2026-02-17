package com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.adapter;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.entity.TaskInMemoryEntity;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.mapper.TaskInMemoryMapper;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.repository.TaskInMemoryRepository;
import com.iv127.task.management.backend.entrypoint.domain.exception.TaskNotFoundException;
import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.UpdateTask;
import com.iv127.task.management.backend.entrypoint.domain.port.out.TaskDBPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Profile("repository-memory")
@Component
@RequiredArgsConstructor
public class TaskInMemoryStorageAdapter implements TaskDBPort {

    private final TaskInMemoryRepository taskInMemoryRepository;

    @Override
    public Task saveTask(CreateTask createTask) {
        TaskInMemoryEntity entity = TaskInMemoryMapper.toEntity(createTask);
        TaskInMemoryEntity taskMySQLEntity = taskInMemoryRepository.save(entity);
        return TaskInMemoryMapper.toDomain(taskMySQLEntity);
    }

    @Override
    public Boolean deleteTaskById(String id) {
        taskInMemoryRepository.deleteById(id);
        return true;
    }

    @Override
    public Task updateTask(UpdateTask updateTask) {
        var builder = taskInMemoryRepository.findById(updateTask.getId())
                .orElseThrow(() -> new TaskNotFoundException("Missing Task ID"))
                .toBuilder();
        if (updateTask.getTitle() != null) {
            builder.title(updateTask.getTitle());
        }
        if (updateTask.getDescription() != null) {
            builder.description(updateTask.getDescription());
        }
        if (updateTask.getPriority() != null) {
            builder.priority(updateTask.getPriority().name());
        }
        if (updateTask.getStatus() != null) {
            builder.status(updateTask.getStatus().name());
        }
        if (updateTask.getDueDate() != null) {
            builder.dueDate(updateTask.getDueDate());
        }

        return TaskInMemoryMapper.toDomain(taskInMemoryRepository.save(builder.build()));
    }

    @Override
    public List<Task> getTasksByFilter(TaskFilter taskFilter) {
        Stream<TaskInMemoryEntity> tasksStream = taskInMemoryRepository.findAll().stream();

        if (taskFilter.getStatus() != null) {
            tasksStream = tasksStream.filter(value -> value.getStatus().equals(taskFilter.getStatus().name()));
        }
        if (taskFilter.getPriority() != null) {
            tasksStream = tasksStream.filter(value -> value.getPriority().equals(taskFilter.getPriority().name()));
        }
        if (taskFilter.getDueDateBefore() != null) {
            tasksStream = tasksStream.filter(value -> value.getDueDate().isBefore(taskFilter.getDueDateBefore()));
        }

        return tasksStream
                .map(TaskInMemoryMapper::toDomain)
                .toList();
    }
}
