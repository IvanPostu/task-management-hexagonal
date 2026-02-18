package com.iv127.task.management.backend.entrypoint.adapters.out.repository.mysql.entity;

import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskMySQLEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false, length = 10)
    private String priority = TaskPriority.NONE.name();

    @Column(nullable = false, length = 15)
    private String status = TaskStatus.CREATED.name();

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }

}
