package pl.edu.wat.testowy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.testowy.entity.Book;

import java.time.LocalDateTime;
import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByCreatedBefore(LocalDateTime date); //js
}
