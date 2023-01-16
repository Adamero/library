package pl.edu.wat.testowy.service;

//import io.micrometer.common.util.StringUtils;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.testowy.dto.AuthorResponse;
import pl.edu.wat.testowy.dto.BookRequest;
import pl.edu.wat.testowy.dto.BookResponse;
import pl.edu.wat.testowy.entity.Author;
import pl.edu.wat.testowy.entity.Book;
import pl.edu.wat.testowy.exception.EntityNotFound;
import pl.edu.wat.testowy.repository.AuthorRepository;
import pl.edu.wat.testowy.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    @Transactional
    public String deleteById(String id) {
        bookRepository.deleteById(id);
        return "Delete";
    }

    public BookResponse save(BookRequest bookRequest) throws EntityNotFound {

        Book book = new Book();

        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        Author author = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(EntityNotFound::new);
        book.setAuthor(author.getId());
        book.setType(bookRequest.getType());
        book.setCreated(LocalDateTime.now());
        book = bookRepository.save(
                book
        );
        return new BookResponse(book.getId(), book.getTitle(), book.getDescription(), new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName()), book.getType(), book.getCreated());
    }

    public BookResponse updateBook(String id, String title, String description) throws EntityNotFound {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFound::new);

        if (StringUtils.hasText(title)) {
            book.setTitle(title);
        }

        if (StringUtils.hasText(description)) {
            book.setDescription(description);
        }

        book = bookRepository.save(book);

        return new BookResponse(book.getId(), book.getTitle(), book.getDescription());
    }
}
