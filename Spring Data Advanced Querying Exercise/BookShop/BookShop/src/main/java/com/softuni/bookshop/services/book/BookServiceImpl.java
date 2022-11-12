package com.softuni.bookshop.services.book;

import com.softuni.bookshop.domein.dto.BookInformation;
import com.softuni.bookshop.domein.entities.Book;
import com.softuni.bookshop.domein.enums.AgeRestriction;
import com.softuni.bookshop.domein.enums.EditionType;
import com.softuni.bookshop.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public boolean isDataSeeded() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    @Override
    public List<Book> findAllByReleaseDateAfter(LocalDate date) {
        final List<Book> books = this.bookRepository
                .findAllByReleaseDateAfter(date)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

        return books;

    }

    @Override
    public List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName) {
        final List<Book> books = this.bookRepository
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getBookTitleReleaseDateCopiesFormat)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByAgeRestriction(String restriction) {
        final AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());

        final List<Book> books = this.bookRepository
                .findAllByAgeRestriction(ageRestriction)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesNumber) {
        final List<Book> books = this.bookRepository
                .findAllByEditionTypeAndCopiesLessThan(type, copiesNumber)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high) {
        final List<Book> books = this.bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(low, high)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getBookTitleAndPriceFormat)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByReleaseDateNot(String year) {
        final List<Book> books = this.bookRepository
                .findAllByReleaseDateStartingWith(year)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(LocalDate date) {
        final List<Book> books = this.bookRepository
                .findAllByReleaseDateBefore(date)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getBookTitleEditionTypeAndPriceFormat)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByTitleContaining(String contains) {
        final List<Book> books = this.bookRepository
                .findAllByTitleContaining(contains)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartingWith(String prefix) {
        final List<Book> books = this.bookRepository
                .findAllByAuthorLastNameStartingWith(prefix)
                .orElseThrow(NoSuchElementException::new);

        books.stream()
                .map(Book::getBookTitleAndAuthorFullNameFormat)
                .forEach(System.out::println);

        return books;
    }

    @Override
    public Integer findCountOfBooksByBookTitleLongerThen(Integer length) {
        final Integer integer = this.bookRepository
                .findCountOfBooksByBookTitleLongerThen(length)
                .orElseThrow(NoSuchElementException::new);

        System.out.println(integer);

        return integer;

    }

    @Override
    public BookInformation findFirstByTitle(String title) {
        final BookInformation bookInformation = this.bookRepository
                .findFirstByTitle(title)
                .orElseThrow(NoSuchElementException::new);

        System.out.println(bookInformation.toString());

        return bookInformation;

    }

    @Override
    public int increaseBookCopies(LocalDate date, int copies) {
        final int count = this.bookRepository.increaseBookCopies(date, copies);

        System.out.println(count * copies);

        return count;
    }

    @Override
    public int deleteAllByCopiesLessThan(Integer copies) {
        final int countOfDeletedObjects = this.bookRepository.deleteAllByCopiesLessThan(copies);

        System.out.println(countOfDeletedObjects);

        return countOfDeletedObjects;
    }

    @Override
    public int getBooksWrittenBy(String name) {
        final int countOfBooks = this.bookRepository.getBooksWrittenBy(name);

        System.out.println(countOfBooks);

        return countOfBooks;
    }


}
