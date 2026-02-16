package com.iv127.task.management.backend.entrypoint.domain.port.out;

import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.UpdateTask;

import java.util.List;

public interface TaskDBPort {

    Task saveTask(CreateTask task);

    Boolean deleteTaskById(String id);

    Task updateTask(UpdateTask updateTask);

    List<Task> getTasksByFilter(TaskFilter taskFilter);
}
