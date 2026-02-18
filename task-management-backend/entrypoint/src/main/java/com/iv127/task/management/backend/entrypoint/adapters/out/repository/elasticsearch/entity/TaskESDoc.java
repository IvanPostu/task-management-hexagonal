package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.entity;

import com.iv127.task.management.backend.entrypoint.domain.model.TaskPriority;
import com.iv127.task.management.backend.entrypoint.domain.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "tasks")
public class TaskESDoc {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String priority = TaskPriority.NONE.name();

    @Field(type = FieldType.Keyword)
    private String status = TaskStatus.CREATED.name();

    @Field(type = FieldType.Date, format = DateFormat.date)
    private LocalDate dueDate;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdDate = Instant.now();
}
