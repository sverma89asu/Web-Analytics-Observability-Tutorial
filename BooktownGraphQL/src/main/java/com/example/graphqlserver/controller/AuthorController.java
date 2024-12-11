package com.example.graphqlserver.controller;

import com.example.graphqlserver.dto.input.AddAuthorInput;
import com.example.graphqlserver.dto.input.UpdateAuthorInput;
import com.example.graphqlserver.dto.output.AddAuthorPayload;
import com.example.graphqlserver.dto.output.UpdateAuthorPayload;
import com.example.graphqlserver.model.Author;
import com.example.graphqlserver.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public List<Author> authors() {
        log.info("Received request to get all authors");
        return authorRepository.getAuthors();
    }

    @QueryMapping
    public  Author authorById(@Argument("id") int id) {
        log.info("Received request to get author by id: [{}]", id);
        return authorRepository.getAuthorById(id);
    }

    @QueryMapping
    public List<Author> authorBylastName(@Argument("lastName") String lastName) {
        log.info("Received request to get author by last name: [{}]", lastName);
        return authorRepository.getAuthorByLastName(lastName);
    }
    @MutationMapping
    public AddAuthorPayload addAuthor(@Argument AddAuthorInput input) {
        log.info("Received request to add an author");
        var author = authorRepository.save(input.lastName(), input.firstName(), input.books());
        var out = new AddAuthorPayload(author);
        return out;
    }

    @MutationMapping
    public UpdateAuthorPayload updateAuthorFirstName(@Argument UpdateAuthorInput input) {
        log.info("Received request to update author first name");
        String authorName =  authorRepository.updateAuthorFirstName(input.authorId(), input.newFirstName());
        var out = new UpdateAuthorPayload(authorName);
        return out;
    }
}
