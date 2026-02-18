package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.adapter;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.entity.TaskESDoc;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.mapper.TaskESMapper;
import com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.repository.TaskElasticsearchRepository;
import com.iv127.task.management.backend.entrypoint.domain.exception.TaskNotFoundException;
import com.iv127.task.management.backend.entrypoint.domain.model.CreateTask;
import com.iv127.task.management.backend.entrypoint.domain.model.Task;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskFilter;
import com.iv127.task.management.backend.entrypoint.domain.model.UpdateTask;
import com.iv127.task.management.backend.entrypoint.domain.port.out.TaskDBPort;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Profile("repository-elasticsearch")
@AllArgsConstructor
@Component
public class TaskESMemoryStorageAdapter implements TaskDBPort {

    private final TaskElasticsearchRepository taskElasticsearchRepository;

    @Override
    public Task saveTask(CreateTask task) {
        TaskESDoc saved = taskElasticsearchRepository.save(TaskESMapper.toDoc(task));
        return TaskESMapper.toDomain(saved);
    }

    @Override
    public Boolean deleteTaskById(String id) {
        taskElasticsearchRepository.deleteById(id);
        return true;
    }

    @Override
    public Task updateTask(UpdateTask updateTask) {
        TaskESDoc existingDoc = taskElasticsearchRepository.findById(updateTask.getId())
                .orElseThrow(() -> new TaskNotFoundException("Missing Task ID"));
        TaskESDoc updatedDoc = TaskESMapper.applyUpdates(existingDoc, updateTask);
        TaskESDoc saved = taskElasticsearchRepository.save(updatedDoc);
        return TaskESMapper.toDomain(saved);
    }

    @Override
    public List<Task> getTasksByFilter(TaskFilter taskFilter) {
        List<TaskESDoc> docs = new LinkedList<>();
        // TODO: dirty hack
        taskElasticsearchRepository.findAll().forEach(docs::add);

        Stream<TaskESDoc> tasksStream = docs.stream();

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
                .map(TaskESMapper::toDomain)
                .toList();
    }
}
