package com.softuni.bookshop.services.author;

import com.softuni.bookshop.domein.entities.Author;
import com.softuni.bookshop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAll(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public Author getRandomAuthor() {
        final long count = this.authorRepository.count();

        if (count != 0) {
            final long randomAuthorId = new Random().nextLong(1L, count) + 1L;

            return this.authorRepository.findAuthorById(randomAuthorId).orElseThrow(NoSuchElementException::new);
        }

        throw new RuntimeException();
    }

    @Override
    public List<Author> findDistinctByBooksBefore(LocalDate date) {
        final List<Author> authors = this.authorRepository
                .findDistinctByBooksReleaseDateBefore(date)
                .orElseThrow(NoSuchElementException::new);

        authors.stream()
                .map(Author::getFullName)
                .forEach(System.out::println);

        return authors;
    }

    @Override
    public List<Author> findAllOrderByBooks() {
        final List<Author> authors = this.authorRepository
                .findAllDistinctOrderByBooks()
                .orElseThrow(NoSuchElementException::new);

        authors.forEach(author -> System.out.println(author.toStringWithCount()));

        return authors;
    }

    @Override
    public List<Author> findAllByFirstNameEndingWith(String suffix) {
        final List<Author> authors = this.authorRepository
                .findAllByFirstNameEndingWith(suffix)
                .orElseThrow(NoSuchElementException::new);

        authors.stream()
                .map(Author::getFullName)
                .forEach(System.out::println);

        return authors;
    }

}
