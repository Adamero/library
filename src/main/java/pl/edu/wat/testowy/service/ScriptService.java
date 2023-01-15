package pl.edu.wat.testowy.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.testowy.repository.AuthorRepository;
import pl.edu.wat.testowy.repository.BookRepository;


@Service
@Slf4j
public class ScriptService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ScriptService(AuthorRepository authorRepository,
                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public String exec(String script){
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()){
            var bindings = context.getBindings("js");
            bindings.putMember("authorRepository",authorRepository);
            bindings.putMember("bookRepository",bookRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }



}

