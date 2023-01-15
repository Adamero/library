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

    @PostMapping("/add-books")
    public ResponseEntity<String> addBooks() {
        String script = """
                    function addBooks() {
                        var Book = Java.type('pl.edu.wat.testowy.entity.Book');
                        var books = [{ title: 't00es00t000w0'}, { title: 't01es01t001w1'}];
                        for (book of books) {
                            if (book.title.includes("0")) {
                                var newBook = new Book();
                                newBook.setTitle(book.title);
                                newBook.setDescription(book.description);
                                bookRepository.save(newBook);
                            }
                        }
                    }
                    addBooks();
                """;

        ScriptService scriptService = new ScriptService(authorRepository, bookRepository);

        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK);
    }

    @GetMapping("/fix-titles")
    public String fixTitles() {
        String script = """
                    
                     function findBooks() {
                           var book = bookRepository.findAll();
                           var test;
                           for (books of bookRepository.findAll()){
                                tst = books.getTitle();
                                if(tst.includes("0")){
                                test = books;
                                books.setTitle(tst.replaceAll("0","O"));
                                bookRepository.save(books);
                                }
                           }                           
                           return book;
                         }
                        
                         console.log(findBooks());
                         findBooks();
                """;

        ScriptService scriptService = new ScriptService(authorRepository, bookRepository);

        return scriptService.exec(script);
    }





}
