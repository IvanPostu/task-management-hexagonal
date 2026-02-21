package com.iv127.task.management.backend.entrypoint.config;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

final class ElasticsearchTestContainer extends GenericContainer<ElasticsearchTestContainer> {

    ElasticsearchTestContainer() {
        super(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:9.2.3"));

        this.withEnv("discovery.type", "single-node")
                .withEnv("xpack.security.enabled", "true")
                .withEnv("ELASTIC_PASSWORD", "qwerty")
                .withEnv("ES_JAVA_OPTS", "-Xms2g -Xmx2g")
                .withExposedPorts(9200, 9300);
    }

}
