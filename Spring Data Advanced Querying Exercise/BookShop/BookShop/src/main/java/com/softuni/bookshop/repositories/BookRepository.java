package com.softuni.bookshop.repositories;

import com.softuni.bookshop.domein.dto.BookInformation;
import com.softuni.bookshop.domein.entities.Book;
import com.softuni.bookshop.domein.enums.AgeRestriction;
import com.softuni.bookshop.domein.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate localDate);

    Optional<List<Book>> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesNumber);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    Optional<List<Book>> findAllByReleaseDateStartingWith(String date);

    Optional<List<Book>> findAllByTitleContaining(String contains);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate date);

    Optional<List<Book>> findAllByAuthorLastNameStartingWith(String prefix);

    @Query("select count(b) from Book b where length(b.title) > :length")
    Optional<Integer> findCountOfBooksByBookTitleLongerThen(Integer length);

    @Query("select new com.softuni.bookshop.domain.dto.BookInformation(b.title, b.editionType,b.ageRestriction, b.price)" +
            " from Book b where b.title = :title")
    Optional<BookInformation> findFirstByTitleWithQuery(String title);

    Optional<BookInformation> findFirstByTitle(String title);

    @Modifying
    @Transactional
    @Query("update Book b set b.copies = b.copies + :copies where b.releaseDate > :date")
    int increaseBookCopies(LocalDate date, int copies);

    @Transactional
    int deleteAllByCopiesLessThan(Integer copies);

    //    create procedure get_books_written_by(IN name varchar(150), out count_out int)
    //    BEGIN
    //    SELECT COUNT(b.id) into count_out
    //    FROM book AS b
    //    JOIN author AS a ON a.id = b.author_id
    //    WHERE CONCAT(a.first_name, ' ', a.last_name) = name;
    //    END;
    @Procedure(value = "get_books_written_by")
    int getBooksWrittenBy(String name);

}

