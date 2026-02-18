package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.repository;

import com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.entity.TaskESDoc;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Profile("repository-elasticsearch")
@Repository
public interface TaskElasticsearchRepository extends ElasticsearchRepository<TaskESDoc, String> {
}
