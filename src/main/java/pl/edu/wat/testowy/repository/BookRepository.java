package pl.edu.wat.testowy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.edu.wat.testowy.entity.Book;

import java.util.Optional;

public interface BookRepository
        extends MongoRepository<Book, String> {


}
