package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.repository;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.entity.TaskESDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskElasticsearchRepository extends ElasticsearchRepository<TaskESDoc, String> {
}
