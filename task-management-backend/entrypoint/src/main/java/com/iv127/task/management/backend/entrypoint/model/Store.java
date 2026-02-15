package com.iv127.task.management.backend.entrypoint.model;

import java.time.Instant;

public record Store(int id, String name, String city, Instant createDate) {
}
