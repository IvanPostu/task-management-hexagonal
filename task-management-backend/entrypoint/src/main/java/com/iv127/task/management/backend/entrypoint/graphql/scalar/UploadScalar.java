package com.iv127.task.management.backend.entrypoint.graphql.scalar;

import graphql.schema.*;
import org.springframework.web.multipart.MultipartFile;

public class UploadScalar {

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Upload")
            .description("Java Upload")
            .coercing(new Coercing<MultipartFile, Void>() {

                @Override
                public MultipartFile parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof MultipartFile) {
                        return (MultipartFile) input;
                    }
                    throw new CoercingParseValueException("Expected a MultipartFile object.");
                }

                @Override
                public MultipartFile parseLiteral(Object input) throws CoercingParseLiteralException {
                    throw new CoercingParseLiteralException("Parsing literal is not supported for Upload.");
                }

                @Override
                public Void serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    throw new CoercingSerializeException("Upload scalar is input-only.");
                }
            })
            .build();
}
