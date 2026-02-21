package com.iv127.task.management.backend.entrypoint.config;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Files;
import java.nio.file.Paths;

final class MySqlTestContainer extends GenericContainer<MySqlTestContainer> {

    private static final String INIT_SCRIPT_PATH = System.getenv("PROJECT_ROOT") + "/init.sql";

    MySqlTestContainer() {
        super(DockerImageName.parse("mysql:9.6.0"));

        validatePreconditions();
        this.withEnv("MYSQL_ROOT_PASSWORD", "secret")
                .withCopyFileToContainer(
                        MountableFile.forHostPath(INIT_SCRIPT_PATH),
                        "/docker-entrypoint-initdb.d/init.sql"
                )
                .withExposedPorts(3306);
    }

    private static void validatePreconditions() {
        if (Files.notExists(Paths.get(INIT_SCRIPT_PATH))) {
            throw new IllegalStateException("Init script couldn't be found, path: " + INIT_SCRIPT_PATH);
        }
    }

}
