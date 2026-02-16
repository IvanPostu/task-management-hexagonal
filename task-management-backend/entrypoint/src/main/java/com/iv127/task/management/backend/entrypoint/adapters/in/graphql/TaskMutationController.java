package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.port.in.TaskManagementUseCase;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TaskMutationController {

    private final TaskManagementUseCase taskManagementUseCase;

    public TaskMutationController(TaskManagementUseCase taskManagementUseCase) {
        this.taskManagementUseCase = taskManagementUseCase;
    }

    @MutationMapping
    public TaskDto createTask(@Argument CreateTaskDto input) {
        Task task = taskManagementUseCase.createTask(TaskGQLMapper.toCreateTask(input));
        return TaskGQLMapper.toTaskDto(task);
    }
}
