//package com.iv127.task.management.backend.entrypoint.graphql;
//
//import graphql.kickstart.tools.GraphQLMutationResolver;
//import graphql.schema.DataFetchingEnvironment;
//import jakarta.servlet.http.Part;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class FileMutation implements GraphQLMutationResolver {
//
//    public FileInfo singleUpload(jakarta.servlet.http.Part file, DataFetchingEnvironment env) throws IOException {
//        // In some setups, use env.getArgument("file") if direct arg is null
//        if (file == null) {
//            file = env.getArgument("file");
//        }
//        if (file.getSize() == 0) {
//            throw new IllegalArgumentException("File is empty");
//        }
//
//        // Process the file (e.g., save to disk)
//        // file.getInputStream() for content; file.transferTo(...) if using MultipartFile cast
//        String filename = file.getSubmittedFileName();
//        long size = file.getSize();
//        String contentType = file.getContentType();
//
//        // Example: Save to /uploads/
//        // try (InputStream is = file.getInputStream()) {
//        //     Files.copy(is, Paths.get("/uploads/" + filename));
//        // }
//
//        return new FileInfo(filename, (int) size, contentType);
//    }
//
//    public List<FileInfo> multipleUpload(List<Part> files, DataFetchingEnvironment env) {
//        return files.stream()
//                .map(file -> {
//                    try {
//                        return singleUpload(file, env);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Failed to process file", e);
//                    }
//                })
//                .collect(Collectors.toList());
//    }
//}
//
