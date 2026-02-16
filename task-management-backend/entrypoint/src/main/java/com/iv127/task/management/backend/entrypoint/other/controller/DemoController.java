package com.iv127.task.management.backend.entrypoint.other.controller;

import com.iv127.task.management.backend.entrypoint.other.model.Employee;
import com.iv127.task.management.backend.entrypoint.other.model.Store;
import com.iv127.task.management.backend.entrypoint.other.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

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

    @SchemaMapping
    public Store location(Employee employee) {
        return demoService.getStore(employee.storeId());
    }

}
