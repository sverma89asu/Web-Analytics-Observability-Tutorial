package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddBookInput;
import com.example.graphqlserver.dto.input.DeleteBookInput;
import com.example.graphqlserver.dto.output.AddBookPayload;
import com.example.graphqlserver.dto.output.DeleteBookPayload;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import com.example.graphqlserver.repository.AuthorRepository;
import com.example.graphqlserver.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Book> books() {
        log.info("Received request to get all books");
        return bookRepository.getBooks();
    }

    @QueryMapping
    public  Book bookByISBN(@Argument("isbn") String isbn) {
        log.info("Received request to get books by ISBN: [{}]", isbn);
        return bookRepository.getBookByISBN(isbn);
    }

    @QueryMapping
    public List<Book> bookByAuthorId(@Argument("authorId") int authorId) {
        log.info("Received request to get books by author id: [{}]", authorId);
        return bookRepository.getBooksByAuthorId(authorId);
    }

    @QueryMapping
    public List<String> bookTitlesByAuthorFirstName(@Argument("firstName") String firstName) {
        log.info("Received request to get books by author first name: [{}]", firstName);
        List<Author> authorsWithFirstName = authorRepository.getAuthorsByFirstName(firstName);
        return bookRepository.getBookTitlesByAuthorFirstName(authorsWithFirstName, firstName);
    }

    @MutationMapping
    public AddBookPayload addBook(@Argument AddBookInput input) {
        log.info("Received request to add a book");
        Author author = authorRepository.getAuthorById(input.authorId());
        if (author == null) {
            throw  new IllegalArgumentException("Author with ID" + input.authorId() + "does not exist");
        }
        var book = bookRepository.save(input.isbn(), input.title(), input.authorId());
        author.getBooks().add(book);
        var out = new AddBookPayload(book);
        return out;
    }

    @MutationMapping
    public DeleteBookPayload deleteBookByISBN(@Argument DeleteBookInput input) {
        log.info("Received request to delete a book");
        String isbn = input.isbn();

        Book deletedBook = bookRepository.deleteBookByISBN(isbn);

        if (deletedBook != null) {
            Author author = authorRepository.getAuthorById(deletedBook.getAuthorId());
            if (author != null) {
                author.getBooks().remove(deletedBook);
            }

            return new DeleteBookPayload(deletedBook.getIsbn());
        }

        throw new IllegalArgumentException("Book with ISBN " + isbn + " not found");
    }



}
