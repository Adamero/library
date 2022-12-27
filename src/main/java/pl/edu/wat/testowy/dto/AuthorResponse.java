package pl.edu.wat.testowy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorResponse {
    private String id;
    private String name;
    private String surname;
}
