package com.iv127.task.management.backend.entrypoint.graphql;

import com.iv127.task.management.backend.entrypoint.graphql.scalar.InstantScalar;
import com.iv127.task.management.backend.entrypoint.graphql.scalar.LocalDateScalar;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.List;

@Configuration
public class GraphQLConfig {

    private static List<GraphQLScalarType> SCALAR_TYPES = List.of(
            InstantScalar.INSTANCE,
            LocalDateScalar.INSTANCE
//            ApolloScalars.Upload
    );

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> {
            SCALAR_TYPES.forEach(wiringBuilder::scalar);
        };
    }

}
