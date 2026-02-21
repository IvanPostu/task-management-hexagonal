package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.task.elasticsearch")
@Getter
@AllArgsConstructor
public class ElasticsearchProperties {
    private String uris;
    private String username;
    private String password;
}
