package pl.edu.wat.testowy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wat.testowy.entity.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
