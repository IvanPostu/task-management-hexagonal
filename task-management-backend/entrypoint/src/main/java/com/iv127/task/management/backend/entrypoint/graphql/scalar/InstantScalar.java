package com.iv127.task.management.backend.entrypoint.graphql.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class InstantScalar {

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Instant")
            .description("Java Instant as ISO-8601 UTC timestamp")
            .coercing(new Coercing<Instant, String>() {

                @Override
                public String serialize(Object dataFetcherResult,
                                        GraphQLContext graphQLContext,
                                        Locale locale) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof Instant instant) {
                        return instant.toString(); // ISO-8601 UTC
                    }
                    throw new CoercingSerializeException("Expected Instant");
                }

                @Override
                public Instant parseValue(Object input,
                                          GraphQLContext graphQLContext,
                                          Locale locale) throws CoercingParseValueException {
                    try {
                        return Instant.parse(input.toString());
                    } catch (DateTimeParseException e) {
                        throw new CoercingParseValueException("Invalid Instant format", e);
                    }
                }

                @Override
                public Instant parseLiteral(Value<?> input,
                                            CoercedVariables variables,
                                            GraphQLContext graphQLContext,
                                            Locale locale) throws CoercingParseLiteralException {

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
