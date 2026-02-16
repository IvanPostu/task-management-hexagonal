package com.iv127.task.management.backend.entrypoint.adapters.in.graphql.config.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateScalar {

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("LocalDate")
            .description("Java LocalDate (ISO-8601 yyyy-MM-dd)")
            .coercing(new Coercing<LocalDate, String>() {

                private static final DateTimeFormatter FORMATTER =
                        DateTimeFormatter.ISO_LOCAL_DATE;

                @Override
                public String serialize(Object dataFetcherResult,
                                        GraphQLContext graphQLContext,
                                        Locale locale) throws CoercingSerializeException {

                    if (dataFetcherResult instanceof LocalDate localDate) {
                        return FORMATTER.format(localDate);
                    }

                    throw new CoercingSerializeException(
                            "Invalid value for LocalDate: " + dataFetcherResult
                    );
                }

                @Override
                public LocalDate parseValue(Object input,
                                            GraphQLContext graphQLContext,
                                            Locale locale) throws CoercingParseValueException {

                    try {
                        return LocalDate.parse(input.toString(), FORMATTER);
                    } catch (Exception e) {
                        throw new CoercingParseValueException(
                                "Invalid date format, expected yyyy-MM-dd", e
                        );
                    }
                }

                @Override
                public LocalDate parseLiteral(Value<?> input,
                                              CoercedVariables variables,
                                              GraphQLContext graphQLContext,
                                              Locale locale)
                        throws CoercingParseLiteralException {

                    if (input instanceof StringValue stringValue) {
                        try {
                            return LocalDate.parse(stringValue.getValue(), FORMATTER);
                        } catch (Exception e) {
                            throw new CoercingParseLiteralException(
                                    "Invalid date format, expected yyyy-MM-dd", e
                            );
                        }
                    }

                    throw new CoercingParseLiteralException(
                            "Expected AST type 'StringValue' for LocalDate"
                    );
                }
            })
            .build();
}
