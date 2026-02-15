package com.iv127.task.management.backend.entrypoint.graphql.scalar;

import graphql.schema.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateScalar {

    public static final GraphQLScalarType LOCAL_DATE = GraphQLScalarType.newScalar()
            .name("LocalDate")
            .description("Java LocalDate")
            .coercing(new Coercing<LocalDate, String>() {
                private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof LocalDate) {
                        return FORMATTER.format((LocalDate) dataFetcherResult);
                    }
                    throw new CoercingSerializeException("Invalid value for LocalDate: " + dataFetcherResult);
                }

                @Override
                public LocalDate parseValue(Object input) throws CoercingParseValueException {
                    try {
                        return LocalDate.parse(input.toString(), FORMATTER);
                    } catch (Exception e) {
                        throw new CoercingParseValueException("Invalid date format, expected yyyy-MM-dd");
                    }
                }

                @Override
                public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
                    return parseValue(input);
                }
            })
            .build();
}
