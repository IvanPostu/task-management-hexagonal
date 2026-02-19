package com.iv127.task.management.backend.entrypoint.adapters.in.graphql;

import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.port.in.TaskManagementUseCase;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@AllArgsConstructor
@Controller
public class TaskController {

    private final TaskManagementUseCase taskManagementUseCase;

    @MutationMapping
    public TaskDto createTask(@Argument CreateTaskDto input) {
        Task task = taskManagementUseCase.createTask(TaskGQLMapper.toCreateTask(input));
        return TaskGQLMapper.toTaskDto(task);
    }

    @MutationMapping
    public TaskDto updateTask(@Argument UpdateTaskDto input) {
        Task task = taskManagementUseCase.updateTask(TaskGQLMapper.toUpdateTask(input));
        return TaskGQLMapper.toTaskDto(task);
    }

    @MutationMapping
    public Boolean deleteTaskById(@Argument String id) {
        return taskManagementUseCase.deleteTaskById(id);
    }

    @QueryMapping
    public List<TaskDto> getTasksByFilter(@Argument TaskFilterInput filter) {
        List<Task> tasks = taskManagementUseCase.getTasksByFilter(TaskGQLMapper.toTaskFilter(filter));
        return tasks.stream().map(TaskGQLMapper::toTaskDto).toList();
    }

}
