package pl.edu.wat.testowy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.testowy.entity.Book;


public interface BookRepository extends MongoRepository<Book, String> {
}
