package pl.edu.wat.testowy.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.testowy.dto.BookRequest;
import pl.edu.wat.testowy.dto.BookResponse;
import pl.edu.wat.testowy.entity.Book;
import pl.edu.wat.testowy.exception.EntityNotFound;
import pl.edu.wat.testowy.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookController {
    //skrot klawiszowy na ctrl + l
    private final BookService bookService;

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
        BookResponse bookResponse = bookService.save(bookRequest);
        return new ResponseEntity<>(bookResponse.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody ResponseEntity delUser(@PathVariable String id) {
        bookService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }//tego nie musze bardziej szczegolowo juz poprawiac

}
