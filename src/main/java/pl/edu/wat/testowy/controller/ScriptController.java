package pl.edu.wat.testowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.testowy.repository.AuthorRepository;
import pl.edu.wat.testowy.repository.BookRepository;
import pl.edu.wat.testowy.service.ScriptService;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {

    private final ScriptService scriptService;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ScriptController(ScriptService scriptService, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.scriptService = scriptService;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @PutMapping()
    public ResponseEntity<String> execScript(@RequestBody String script) {
        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK);
    }

    //var Article = Java.type('pl.edu.wat.testowy.entity.Book');
    //var Author = Java.type('pl.edu.wat.testowy.entity.Author');
    //var Set = Java.type('java.util.Set');

    //var patrycjaAuthor = new Author();
    //               patrycjaAuthor.setFirstName("Aam");
    //               patrycjaAuthor.setLastName("xd");
    //               authorRepository.save(patrycjaAuthor);


    @GetMapping("/test")
    public String execScript2() {
        //return bookService.getAllBooks();
        /*
         var bookId = "63ab34bc5f3fa84ab5703eef";
                       bookRepository.findAll();
                       var books = bookRepository.findAll();
                       books.forEach(function(book) {
                         console.log(book.title);
                       });
         */
        String script = """   
                       authorRepository.findAll()
                """;

        ScriptService scriptService = new ScriptService(authorRepository, bookRepository);

        return scriptService.exec(script);
    }
//bookRepository.findAll();
}
