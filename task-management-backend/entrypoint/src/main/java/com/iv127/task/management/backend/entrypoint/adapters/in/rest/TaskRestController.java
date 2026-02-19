package com.iv127.task.management.backend.entrypoint.adapters.in.rest;

import com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto.TaskRestCreateRequest;
import com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto.TaskRestResponse;
import com.iv127.task.management.backend.entrypoint.adapters.in.rest.dto.TaskRestUpdateRequest;
import com.iv127.task.management.backend.entrypoint.adapters.in.rest.mapper.TaskRestMapper;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import com.iv127.task.management.backend.entrypoint.domain.port.in.TaskManagementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskManagementUseCase taskManagementUseCase;

    @PostMapping
    public ResponseEntity<TaskRestResponse> createTask(@RequestBody TaskRestCreateRequest request) {
        Task task = taskManagementUseCase.createTask(TaskRestMapper.toCreateTask(request));
        return ResponseEntity.ok(TaskRestMapper.toTaskResponse(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskRestResponse>> getTasks(
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) LocalDate dueDateBefore) {

        TaskFilter filter = TaskFilter.builder()
                .priority(priority)
                .status(status)
                .dueDateBefore(dueDateBefore)
                .build();

        List<Task> tasks = taskManagementUseCase.getTasksByFilter(filter);
        List<TaskRestResponse> responses = tasks.stream()
                .map(TaskRestMapper::toTaskResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskRestResponse> updateTask(
            @PathVariable String id,
            @RequestBody TaskRestUpdateRequest taskRestUpdateRequest) {

        Task task = taskManagementUseCase.updateTask(TaskRestMapper.toUpdateTask(id, taskRestUpdateRequest));
        return ResponseEntity.ok(TaskRestMapper.toTaskResponse(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskManagementUseCase.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
