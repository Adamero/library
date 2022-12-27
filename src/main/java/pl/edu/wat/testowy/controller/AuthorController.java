package pl.edu.wat.testowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.testowy.dto.AuthorRequest;
import pl.edu.wat.testowy.dto.AuthorResponse;
import pl.edu.wat.testowy.entity.Author;
import pl.edu.wat.testowy.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> fetchAllAuthor() {
        return authorService.getAllAuthor();
    }

    @PostMapping
    public ResponseEntity<String> createAuthor(@RequestBody AuthorRequest authorRequest) {
        AuthorResponse authorResponse = authorService.save(authorRequest);
        return new ResponseEntity<>(authorResponse.getId(), HttpStatus.CREATED);
    }


}
