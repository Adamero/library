package pl.edu.wat.testowy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class Author {
    @Id
    private String id;
    private String firstName;
    private String lastName;

    public Author() {

    }
}
