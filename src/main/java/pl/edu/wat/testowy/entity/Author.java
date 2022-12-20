package pl.edu.wat.testowy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
public class Author {
    private String firstName;
    private String lastName;

    public Author() {

    }
}
