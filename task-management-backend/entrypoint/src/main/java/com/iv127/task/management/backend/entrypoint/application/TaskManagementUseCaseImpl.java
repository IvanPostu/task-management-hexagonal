package com.iv127.task.management.backend.entrypoint.application;

import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.UpdateTask;
import com.iv127.task.management.backend.entrypoint.domain.port.in.TaskManagementUseCase;
import com.iv127.task.management.backend.entrypoint.domain.port.out.TaskDBPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskManagementUseCaseImpl implements TaskManagementUseCase {

    private final TaskDBPort taskDBPort;

    @Override
    public Task createTask(CreateTask task) {
        return taskDBPort.saveTask(task);
    }

    @Override
    public Boolean deleteTaskById(String id) {
        return taskDBPort.deleteTaskById(id);
    }

    @Override
    public Task updateTask(UpdateTask updateTask) {
        return taskDBPort.updateTask(updateTask);
    }

    @Override
    public List<Task> getTasksByFilter(TaskFilter taskFilter) {
        return taskDBPort.getTasksByFilter(taskFilter);
    }
}
