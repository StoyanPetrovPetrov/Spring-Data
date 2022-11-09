package com.softuni.bookshop.services.book;

import com.softuni.bookshop.domein.entities.Book;
import com.softuni.bookshop.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    @Override
    public List<Book> findAllByReleaseDateAfter(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateAfter(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName)
                .orElseThrow(NoSuchElementException::new);
    }
}
