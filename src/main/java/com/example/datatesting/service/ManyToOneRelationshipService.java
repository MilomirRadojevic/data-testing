package com.example.datatesting.service;

import com.example.datatesting.entity.Author;
import com.example.datatesting.entity.Book;
import com.example.datatesting.repository.AuthorRepository;
import com.example.datatesting.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManyToOneRelationshipService {
    // use this if we don't need other side of the relationship
    // specify lazy
    // no cascading or orphan removal
    // there is only one side so no syncing like in bidirectional
    // equals, hash code, to string overriding
    // specify join column

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private static final String AUTHOR_NAME = "auth name";
    private static final Long AUTHOR_ID = 1L;
    private static final String BOOK_1_OLD_NAME = "b1 old name";
    private static final String BOOK_1_NEW_NAME = "b1 new name";
    private static final String BOOK_2_NAME = "b2 name";

    @Transactional
    public void addAuthor() {
        Author author = new Author();
        author.setName(AUTHOR_NAME);

        authorRepository.save(author);
    }

    @Transactional
    public void addBook() {
        Author author = authorRepository.findById(AUTHOR_ID)
            .orElseThrow(() -> new RuntimeException("Demonstration error"));

        Book book = new Book();
        book.setName(BOOK_1_OLD_NAME);
        book.setAuthor(author);

        bookRepository.save(book);

        book.setName(BOOK_1_NEW_NAME);
    }

    @Transactional
    public void findBooksOfAuthorByIdAndUpdateName() {
        List<Book> books = bookRepository.findBooksOfAuthorById(AUTHOR_ID);

        books.get(0).setName(BOOK_1_OLD_NAME);
    }

    @Transactional
    public void findBooksOfAuthorByIdAndAddNewBook() {
        List<Book> books = bookRepository.findBooksOfAuthorById(AUTHOR_ID);

        Book book = new Book();
        book.setName(BOOK_2_NAME);
        book.setAuthor(books.get(0).getAuthor());

        books.add(bookRepository.save(book));
    }

    @Transactional
    public void findBooksOfAuthorByIdAndRemoveTheirFirstBook() {
        List<Book> books = bookRepository.findBooksOfAuthorById(AUTHOR_ID);

        bookRepository.delete(books.remove(0));
    }
}
