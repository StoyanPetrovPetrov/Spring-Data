package com.softuni.gamestore;

import com.softuni.gamestore.services.interfaces.AppEngine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final AppEngine engine;

    public ConsoleRunner(AppEngine engine) {
        this.engine = engine;
    }

    @Override
    public void run(String... args) throws Exception {
        engine.run();
    }
}
