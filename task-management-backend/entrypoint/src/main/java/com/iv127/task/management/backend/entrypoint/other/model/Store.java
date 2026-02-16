package com.iv127.task.management.backend.entrypoint.other.model;

import java.time.Instant;
import java.time.LocalDate;

public record Store(int id, String name, String city, Instant createDate, LocalDate dueDate) {
}
