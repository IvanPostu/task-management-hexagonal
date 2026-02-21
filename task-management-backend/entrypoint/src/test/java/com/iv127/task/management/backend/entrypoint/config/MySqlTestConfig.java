package com.iv127.task.management.backend.entrypoint.config;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MySqlTestConfig {
    private final MySqlTestContainer mySqlContainer;

    MySqlTestConfig() {
        this.mySqlContainer = new MySqlTestContainer();
        mySqlContainer.start();
    }

    @Bean
    public DataSourceProperties taskDataSourceProperties() {
        String url = String.format(
                "jdbc:mysql://%s:%s/demo1?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                mySqlContainer.getHost(), mySqlContainer.getMappedPort(3306));

        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(url);
        properties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        properties.setUsername("root");
        properties.setPassword("secret");
        return properties;
    }

    @PreDestroy
    void tearDown() {
        mySqlContainer.close();
    }
}
