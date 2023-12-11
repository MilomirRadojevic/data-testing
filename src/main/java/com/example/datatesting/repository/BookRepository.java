package com.example.datatesting.repository;

import com.example.datatesting.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.author.id = :id")
    List<Book> findBooksOfAuthorById(Long id);
}
