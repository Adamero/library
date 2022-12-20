package pl.edu.wat.testowy.service;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.testowy.dto.BookRequest;
import pl.edu.wat.testowy.dto.BookResponse;
import pl.edu.wat.testowy.entity.Author;
import pl.edu.wat.testowy.entity.Book;
import pl.edu.wat.testowy.exception.EntityNotFound;
import pl.edu.wat.testowy.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    @Transactional
    public String deleteById(String id){
        bookRepository.deleteById(id);
        return "Delete";
    }

    public BookResponse save(BookRequest bookRequest){
        //Book book = new Book();
        Author author = new Author();
        author.setFirstName("Imie");
        author.setLastName("Naziwsko");
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        //book.setType(List.of("Sci-fi", "Drama"));
        book.setAuthor(author);
        book.setType(bookRequest.getType());
        book.setCreated(LocalDateTime.now());
        book = bookRepository.save(
                book
        );
        return new BookResponse(book.getId(),book.getTitle(),book.getDescription(),book.getType(),book.getCreated());
    }

    public BookResponse update(String id, String title, String description,List<String> type){
        Optional<Book> existingBook = bookRepository.findById(id);
        if (!existingBook.isPresent()) {



        }
        return null;
    }

    public BookResponse updateBook(String id, String title, String description) throws EntityNotFound {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFound::new);
        if(StringUtils.isNotBlank(title)) {
            book.setTitle(title);
        }

        if(StringUtils.isNotBlank(description)) {
            book.setDescription(description);
        }

        book = bookRepository.save(book);

        return new BookResponse(book.getId(), book.getTitle(), book.getDescription());
    }

/*
    @Bean
    CommandLineRunner runner(BookRepository repository, MongoTemplate mongoTemplate){
        return args -> {
            Author author = new Author(
                    "Ania",
                    "Zielonka"
            );

            String title = "Ania z zielonego wzgorza";
            Book book = new Book(
                    title,
                    "Ksiazka o jakiejs tam Ani",
                    author,
                    List.of("Sci-fi", "Drama"),
                    LocalDateTime.now()
            );
            repository.findBookByTytul(title)
                    .ifPresentOrElse(b -> {
                        System.out.println(book + "already exists");
                    }, () -> {
                        System.out.println("Inserting book " + book);
                        repository.insert(book);
                    });
     */
}
