package pl.edu.wat.testowy.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Book {
    @Id
    private String id;
    private String title;
    @Indexed(unique = true)
    private String description;
    private Author author;
    private List<String> type;
    private LocalDateTime created;

    public Book(String title,
                String description,
                Author author,
                List<String> type,
                LocalDateTime created) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.type = type;
        this.created = created;
    }

    public Book() {

    }
}
