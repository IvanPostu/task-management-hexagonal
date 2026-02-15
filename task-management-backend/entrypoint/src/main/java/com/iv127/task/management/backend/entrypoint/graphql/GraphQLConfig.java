package com.iv127.task.management.backend.entrypoint.graphql;

import com.iv127.task.management.backend.entrypoint.graphql.scalar.InstantScalar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder ->
                wiringBuilder.scalar(InstantScalar.INSTANT);
    }
}
