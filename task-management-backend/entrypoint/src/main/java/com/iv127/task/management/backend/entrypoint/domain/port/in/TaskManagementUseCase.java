package com.iv127.task.management.backend.entrypoint.domain.port.in;

import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.UpdateTask;

import java.util.List;

public interface TaskManagementUseCase {

    Task createTask(CreateTask createTask);

    Boolean deleteTaskById(String id);

    Task updateTask(UpdateTask updateTask);

    List<Task> getTasksByFilter(TaskFilter taskFilter);
}
