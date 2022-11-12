package com.softuni.bookshop;

import com.softuni.bookshop.services.author.AuthorService;
import com.softuni.bookshop.services.book.BookService;
import com.softuni.bookshop.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final Scanner scanner;

    private final SeedService seedService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public ConsoleRunner(BookService bookService,
                         AuthorService authorService,
                         SeedService seedService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.seedService = seedService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws IOException {
        this.seedService.seedAllData();
//        place your method here and test it with data
//        final String copies = scanner.nextLine();
    }


}
