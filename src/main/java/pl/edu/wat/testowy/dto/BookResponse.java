package pl.edu.wat.testowy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.wat.testowy.entity.Book;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookResponse {
    private String id;
    private String title;
    private String description;
    private AuthorResponse author;
    private List<String> type;
    private LocalDateTime created;

    public BookResponse(Book updatedBook) {
    }

    public BookResponse(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
