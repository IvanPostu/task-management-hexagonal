package com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.repository;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.memory.entity.TaskInMemoryEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TaskInMemoryRepository {

    private final Map<String, TaskInMemoryEntity> memory = new ConcurrentHashMap<>();
    private final AtomicInteger idDummy = new AtomicInteger(0);

    public TaskInMemoryEntity save(TaskInMemoryEntity entity) {
        var toBeSavedBuilder = entity.toBuilder();
        boolean isNew = entity.getId() == null;
        if (isNew) {
            toBeSavedBuilder
                    .id(idDummy.incrementAndGet() + "")
                    .createdDate(Instant.now());
        }
        var toBeSaved = toBeSavedBuilder.build();
        memory.put(toBeSaved.getId(), toBeSaved);
        return toBeSaved;
    }

    public void deleteById(String id) {
        memory.remove(id);
    }

    public Optional<TaskInMemoryEntity> findById(String id) {
        return Optional.ofNullable(memory.get(id));
    }

    public Collection<TaskInMemoryEntity> findAll() {
        return memory.values();
    }

}
