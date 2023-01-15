package pl.edu.wat.testowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.testowy.dto.AuthorRequest;
import pl.edu.wat.testowy.dto.AuthorResponse;
import pl.edu.wat.testowy.entity.Author;
import pl.edu.wat.testowy.exception.EntityNotFound;
import pl.edu.wat.testowy.mapper.AuthorMapper;
import pl.edu.wat.testowy.repository.AuthorRepository;

import java.util.List;


@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) throws EntityNotFound {
        return authorRepository.findById(id).orElseThrow(EntityNotFound::new);
    }

    public AuthorResponse save(AuthorRequest authorRequest) {

        Author author = authorMapper.map(authorRequest);

        author = authorRepository.save(
                author
        );

        return authorMapper.map(author);
        /*Author author = new Author();
        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());
        author = authorRepository.save(
                author
        );
        return new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName());

         */
    }


}
