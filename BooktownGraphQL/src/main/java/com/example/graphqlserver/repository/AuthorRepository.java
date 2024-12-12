package com.example.graphqlserver.repository;

import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public class AuthorRepository {
    private static ArrayList<Author> dummyAuthors = new ArrayList<>();

    static {
        Author author1 = new Author(0, "Robert", "Frost", BookRepository.getBooksByAuthorId(0));
        Author author2 = new Author(1, "Martin", "Fowler", BookRepository.getBooksByAuthorId(1));
        Author author3 = new Author(2, "Kevin", "Gary", BookRepository.getBooksByAuthorId(2));

        dummyAuthors.addAll(Arrays.asList(author1, author2, author3));
    }

    public List<Author> getAuthors() {
        return dummyAuthors;
    }

    public Author getAuthorById(int id) {
        for (Author author : dummyAuthors) {
            if (author.getId() == id) {
                return author;
            }
        }
        return null;
    }

    public Author save(String firstName, String lastName, List<Book> books) {
        int nextId = dummyAuthors.isEmpty() ? 0 : dummyAuthors.get(dummyAuthors.size() - 1).getId() + 1;

        Author newAuthor = new Author(nextId, lastName, firstName, books);

        dummyAuthors.add(newAuthor);

        return newAuthor;
    }

    public List<Author> getAuthorByLastName(String lastName) {
        List<Author> authorWithLastName = new ArrayList<>();
        for(Author author: dummyAuthors) {
            if (author.getLastName().equalsIgnoreCase(lastName)) {
                authorWithLastName.add(author);
            }
        }
        return authorWithLastName;

    }

    public String updateAuthorFirstName(int authorId, String newFirstName) {
        for (Author author : dummyAuthors) {
            if (author.getId() == authorId) {
                String authorName = author.getFirstName();
                author.setFirstName(newFirstName);
                return authorName;
            }
        }
        return null;
    }

    public List<Author> getAuthorsByFirstName(String firstName) {
        List<Author> authorsWithFirstName = new ArrayList<>();
        for (Author author : dummyAuthors) {
            if (author.getFirstName().equalsIgnoreCase(firstName)) {
                authorsWithFirstName.add(author);
            }
        }
        return authorsWithFirstName;
    }


}
