package com.softuni.bookshop.services.book;

import com.softuni.bookshop.domein.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks(List<Book> books);

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);



    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);
}

