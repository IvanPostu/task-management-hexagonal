package com.iv127.task.management.backend.entrypoint.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.*;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class InstantScalar {

    public static final GraphQLScalarType INSTANT = GraphQLScalarType.newScalar()
            .name("Instant")
            .description("Java Instant as ISO-8601 UTC timestamp")
            .coercing(new Coercing<>() {

                @Override
                public String serialize(Object dataFetcherResult) {
                    if (dataFetcherResult instanceof Instant instant) {
                        return instant.toString(); // ISO-8601
                    }
                    throw new CoercingSerializeException("Expected Instant");
                }

                @Override
                public Instant parseValue(Object input) {
                    try {
                        return Instant.parse(input.toString());
                    } catch (DateTimeParseException e) {
                        throw new CoercingParseValueException("Invalid Instant format", e);
                    }
                }

                @Override
                public Instant parseLiteral(Object input) {
                    if (input instanceof StringValue stringValue) {
                        try {
                            return Instant.parse(stringValue.getValue());
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseLiteralException("Invalid Instant format", e);
                        }
                    }
                    throw new CoercingParseLiteralException("Expected StringValue");
                }
            })
            .build();
}
