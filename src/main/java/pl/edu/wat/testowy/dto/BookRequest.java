package pl.edu.wat.testowy.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookRequest {
    private String title;
    private String description;
    private String authorId;
    private List<String> type;
    private LocalDateTime created;
}
