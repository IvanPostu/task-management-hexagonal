package com.iv127.task.management.backend.entrypoint;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        Object o = args;
        LOG.info("Arguments: {}", o);
    }
}
