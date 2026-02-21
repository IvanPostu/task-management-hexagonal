package com.iv127.task.management.backend.entrypoint.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@TestConfiguration
public class ElasticsearchTestConfig {
    private final ElasticsearchTestContainer testContainer = new ElasticsearchTestContainer();

    @PostConstruct
    void init() {
        testContainer.start();
    }

    @Bean
    public Properties taskElasticsearchProperties() {
        String url = String.format("%s:%s", testContainer.getHost(), testContainer.getMappedPort(9200));
        Properties properties = new Properties();
        properties.setProperty("uris", url);
        properties.setProperty("username", "elastic");
        properties.setProperty("password", "qwerty");
        return properties;
    }

    @PreDestroy
    void tearDown() {
        testContainer.close();
    }
}
