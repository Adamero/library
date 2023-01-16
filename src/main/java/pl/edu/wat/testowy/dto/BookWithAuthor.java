package pl.edu.wat.testowy.dto;

import lombok.Data;


@Data
public class BookWithAuthor {
    private String id;
    private String title;
    private String description;
    private AuthorResponse authorObject;

    public BookWithAuthor(String id, String title, String description, AuthorResponse authorObject) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorObject = authorObject;
    }
}