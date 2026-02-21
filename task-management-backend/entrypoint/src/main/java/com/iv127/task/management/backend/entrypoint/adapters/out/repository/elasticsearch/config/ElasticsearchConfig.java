package com.iv127.task.management.backend.entrypoint.adapters.out.repository.elasticsearch.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@EnableConfigurationProperties({
        ElasticsearchProperties.class,
})
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private final ElasticsearchProperties properties;

    public ElasticsearchConfig(ElasticsearchProperties properties) {
        this.properties = properties;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(properties.getUris())
                .withBasicAuth(properties.getUsername(), properties.getPassword())
                .build();
    }
}
