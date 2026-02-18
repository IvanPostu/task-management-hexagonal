package com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.specification;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.entity.TaskMySQLEntity;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecifications {

    public static Specification<TaskMySQLEntity> hasStatus(TaskStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status.name());
    }

    public static Specification<TaskMySQLEntity> hasPriority(TaskPriority priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority.name());
    }

    public static Specification<TaskMySQLEntity> dueDateBefore(LocalDate dueDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), dueDate);
    }

}
