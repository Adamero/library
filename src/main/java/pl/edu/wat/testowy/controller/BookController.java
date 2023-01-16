package pl.edu.wat.testowy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.testowy.dto.AuthorResponse;
import pl.edu.wat.testowy.dto.BookRequest;
import pl.edu.wat.testowy.dto.BookResponse;
import pl.edu.wat.testowy.dto.BookWithAuthor;
import pl.edu.wat.testowy.entity.Book;
import pl.edu.wat.testowy.exception.EntityNotFound;
import pl.edu.wat.testowy.service.AuthorService;
import pl.edu.wat.testowy.service.BookService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/books")
public class BookController {
    //skrot klawiszowy na ctrl + alt + l
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public List<Book> fetchAllBooks() {
        return bookService.getAllBooks();
    }

/*
    @PostMapping()
    public ResponseEntity<String> createAuthor(@RequestParam BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.save(bookRequest).getId(), HttpStatus.CREATED);
    }
*/

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateAuthor(@PathVariable String id, @RequestParam(required = false) String title, @RequestParam(required = false) String description) {
        try {
            return new ResponseEntity<>(bookService.updateBook(id, title, description), HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//http://localhost:8080/api/v1/books/63987aad39e0490a1d82ebcc?title=Nowy%20tytuł&description=Nowy%20opis

    @PostMapping()
    public ResponseEntity<String> createAuthor(@RequestBody BookRequest bookRequest) {
        try {
            return new ResponseEntity<>(bookService.save(bookRequest).getId(), HttpStatus.CREATED);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody ResponseEntity delUser(@PathVariable String id) {
        bookService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }//tego nie musze bardziej szczegolowo juz poprawiac
/*
    @GetMapping("/with-authors")
    public List<BookWithAuthor> fetchAllBooksWithAuthors() {
        List<Book> books = bookService.getAllBooks();
        return books.stream()
                .map(book -> {
                    try {
                        String authorId = book.getAuthor();
                        System.out.println(authorId);
                        AuthorResponse authorObject = new AuthorResponse(authorId, authorService.getAuthorById(authorId).getFirstName(), authorService.getAuthorById(authorId).getLastName());
                        return new BookWithAuthor(book.getId(), book.getTitle(), book.getDescription(), authorObject);
                    } catch (EntityNotFound e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
*/

    @GetMapping("/with-authors")
    public List<BookWithAuthor> fetchAllBooksWithAuthors() {
        List<Book> books = bookService.getAllBooks();
        return books.stream()
                .map(book -> {
                    try {
                        String authorId = book.getAuthor();
                        if(authorId != null) {
                            AuthorResponse authorObject = new AuthorResponse(authorId, authorService.getAuthorById(authorId).getFirstName(), authorService.getAuthorById(authorId).getLastName());
                            return new BookWithAuthor(book.getId(), book.getTitle(), book.getDescription(), authorObject);
                        } else {
                            return new BookWithAuthor(book.getId(), book.getTitle(), book.getDescription(), null);
                        }
                    } catch (EntityNotFound e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(book -> book.getAuthorObject() != null)
                .collect(Collectors.toList());
    }


}
