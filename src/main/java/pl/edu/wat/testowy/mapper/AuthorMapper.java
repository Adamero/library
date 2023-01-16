package pl.edu.wat.testowy.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.testowy.dto.AuthorRequest;
import pl.edu.wat.testowy.dto.AuthorResponse;
import pl.edu.wat.testowy.entity.Author;

@Service
public class AuthorMapper {

    public Author map(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());
        fillAuthorRequest(author, authorRequest); //pierwszy argument to co zwracamy a drugi z czego mapujemy
        return author;
    }

    private void fillAuthorRequest(Author author, AuthorRequest authorRequest) {
        //author.setRank(authorRequest.getRank());
        //empty for byte buddy
    }

    //za pomoca bytebuddy bedziemy podmieniali cialo tej metody
    //ktora dodatkowo wykona cos takiego jak author.setRank(authorRequest.getRank());
    public AuthorResponse map(Author author) {
        AuthorResponse authorResponse = new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName());
        fillAuthor(authorResponse, author);
        return authorResponse;
    }

    private void fillAuthor(AuthorResponse authorResponse, Author author) {
        //authorResponse.setRank(author.getRank());
        //empty for byte buddy
        //w ten sposob zwrocony zostanie request ktory ma rowny stopien wojskowy
    }


//mapuje, z author na authorresponse a z authorrequest na author
    //z author na authorresponse

    //mapper to klasy ktorych odpowiedzialnoscia bedzie przemapowanie z jednego typu na drugi
}
