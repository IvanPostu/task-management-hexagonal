package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Properties;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private final ElasticsearchConfig self;

    public ElasticsearchConfig(@Lazy ElasticsearchConfig self) {
        this.self = self;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.task.elasticsearch")
    @ConditionalOnMissingBean(name = "taskElasticsearchProperties")
    public Properties taskElasticsearchProperties() {
        return new Properties();
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        Properties properties = self.taskElasticsearchProperties();

        String hostAndPort = properties.getProperty("uris");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        return ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .withBasicAuth(username, password)
                .build();
    }
}
