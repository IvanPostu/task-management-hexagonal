package com.iv127.task.management.backend.entrypoint.other.service;

import com.iv127.task.management.backend.entrypoint.other.model.Employee;
import com.iv127.task.management.backend.entrypoint.other.model.Store;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class DemoService {

    private static final List<Store> STORES = List.of(
            new Store(1, "Test1", "NYC1", Instant.parse("2026-01-15T10:15:30Z"), LocalDate.parse("2026-12-25")),
            new Store(2, "Test2", "NYC2", Instant.parse("2026-02-15T10:15:30Z"), LocalDate.parse("2026-12-25")),
            new Store(3, "Test3", "NYC3", Instant.parse("2026-03-15T10:15:30Z"), LocalDate.parse("2026-12-25"))
    );

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(10, "Jim1", 1),
            new Employee(20, "Jim2", 2),
            new Employee(30, "Jim3", 3)
    );

    public List<Store> getAllStores() {
        return STORES;
    }

    public Employee getEmployee(int id) {
        return EMPLOYEES.stream()
                .filter(value -> value.id() == id)
                .findFirst()
                .orElse(null);
    }

    public Store getStore(int id) {
        return STORES.stream()
                .filter(value -> value.id() == id)
                .findFirst()
                .orElse(null);
    }

}
