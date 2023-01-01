package pl.edu.wat.testowy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.wat.testowy.repository.AuthorRepository;
import pl.edu.wat.testowy.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScriptServiceTest {

    @Autowired
    ScriptService scriptService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCalc(){


        String script = """
                    var x = 1;
                    var y = 2;
                    x + y;
                """;

        assert scriptService.exec(script).equals("3");
    }

    @Test
    public void testAddAuthor(){


        String script = """
                    var Article = Java.type('pl.edu.wat.testowy.entity.Book');
                    var Author = Java.type('pl.edu.wat.testowy.entity.Author');
                    var Set = Java.type('java.util.Set');
                    
                    var patrycjaAuthor = new Author();
                    patrycjaAuthor.setFirstName("Aam");
                    patrycjaAuthor.setLastName("xd");
                    authorRepository.save(patrycjaAuthor);
                """;
        ScriptService scriptService = new ScriptService(authorRepository,bookRepository);

        assert scriptService.exec(script) != null;
    }

    @Test
    public void testCheckBooks(){


        String script = """
                        var Article = Java.type('pl.edu.wat.testowy.entity.Book'); 
                        var Author = Java.type('pl.edu.wat.testowy.entity.Author'); 
                        var Get = Java.type('java.util.Get');
                        var test = new Author();
                        
                        if(test.getFirstName() == "Ania"){
                            authorRepository.findAll();
                        }
                        
                """;

        ScriptService scriptService = new ScriptService(authorRepository,bookRepository);

        assert scriptService.exec(script) != null;

    }
}