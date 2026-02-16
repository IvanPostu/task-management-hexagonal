package com.iv127.task.management.backend.entrypoint.controller;

import com.iv127.task.management.backend.entrypoint.graphql.FileInfo;
import com.iv127.task.management.backend.entrypoint.model.Employee;
import com.iv127.task.management.backend.entrypoint.model.Store;
import com.iv127.task.management.backend.entrypoint.service.DemoService;
import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @QueryMapping
    public Employee getEmployee(@Argument int id) {
        return demoService.getEmployee(id);
    }

    @QueryMapping
    public List<Store> getAllStores() {
        return demoService.getAllStores();
    }

    // DataFetchingEnvironment should be last argument
//    @MutationMapping
//    public String uploadFile(DataFetchingEnvironment environment) {
//        var context = environment.getGraphQlContext();
//
//
//        return "aa11";
//    }

    @MutationMapping
    public FileInfo singleUpload(@Argument Part file, DataFetchingEnvironment env) throws IOException {
        // In some setups, use env.getArgument("file") if direct arg is null
        DefaultGraphQLServletContext context = env.getContext();
        if (file == null) {
            file = env.getArgument("file");
        }
        if (file.getSize() == 0) {
            throw new IllegalArgumentException("File is empty");
        }

        // Process the file (e.g., save to disk)
        // file.getInputStream() for content; file.transferTo(...) if using MultipartFile cast
        String filename = file.getSubmittedFileName();
        long size = file.getSize();
        String contentType = file.getContentType();

        // Example: Save to /uploads/
        // try (InputStream is = file.getInputStream()) {
        //     Files.copy(is, Paths.get("/uploads/" + filename));
        // }

        return new FileInfo(filename, (int) size, contentType);
    }

    @SchemaMapping
    public Store location(Employee employee) {
        return demoService.getStore(employee.storeId());
    }

}
