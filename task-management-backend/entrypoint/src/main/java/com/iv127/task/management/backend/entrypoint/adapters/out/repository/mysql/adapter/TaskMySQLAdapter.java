package com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.adapter;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.entity.TaskMySQLEntity;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.mapper.TaskMySQLMapper;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.repository.TaskMySQLRepository;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.specification.TaskSpecifications;
import com.iv127.task.management.backend.entrypoint.domain.exception.TaskNotFoundException;
import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.UpdateTask;
import com.iv127.task.management.backend.entrypoint.domain.port.out.TaskDBPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("repository-mysql")
@Component
@RequiredArgsConstructor
public class TaskMySQLAdapter implements TaskDBPort {

    private final TaskMySQLRepository taskMySQLRepository;

    @Override
    public Task saveTask(CreateTask createTask) {
        TaskMySQLEntity entity = TaskMySQLMapper.toEntity(createTask);
        TaskMySQLEntity taskMySQLEntity = taskMySQLRepository.save(entity);
        return TaskMySQLMapper.toDomain(taskMySQLEntity);
    }

    @Override
    public Boolean deleteTaskById(String id) {
        taskMySQLRepository.deleteById(Integer.valueOf(id));
        return true;
    }

    @Override
    public Task updateTask(UpdateTask updateTask) {
        TaskMySQLEntity task =
                taskMySQLRepository.findById(Integer.valueOf(updateTask.getId()))
                        .orElseThrow(() -> new TaskNotFoundException("Missing Task ID"));

        if (updateTask.getTitle() != null) {
            task.setTitle(updateTask.getTitle());
        }
        if (updateTask.getDescription() != null) {
            task.setDescription(updateTask.getDescription());
        }
        if (updateTask.getPriority() != null) {
            task.setPriority(updateTask.getPriority().name());
        }
        if (updateTask.getStatus() != null) {
            task.setStatus(updateTask.getStatus().name());
        }
        if (updateTask.getDueDate() != null) {
            task.setDueDate(updateTask.getDueDate());
        }

        return TaskMySQLMapper.toDomain(taskMySQLRepository.save(task));
    }

    @Override
    public List<Task> getTasksByFilter(TaskFilter taskFilter) {
        Specification<TaskMySQLEntity> spec = Specification
                .where((root, query, cb) -> cb.conjunction());

        if (taskFilter.getStatus() != null) {
            spec = spec.and(TaskSpecifications.hasStatus(taskFilter.getStatus()));
        }
        if (taskFilter.getPriority() != null) {
            spec = spec.and(TaskSpecifications.hasPriority(taskFilter.getPriority()));
        }
        if (taskFilter.getDueDateBefore() != null) {
            spec = spec.and(TaskSpecifications.dueDateBefore(taskFilter.getDueDateBefore()));
        }

        List<TaskMySQLEntity> tasks = taskMySQLRepository.findAll(spec);

        return tasks.stream()
                .map(TaskMySQLMapper::toDomain)
                .toList();
    }
}
