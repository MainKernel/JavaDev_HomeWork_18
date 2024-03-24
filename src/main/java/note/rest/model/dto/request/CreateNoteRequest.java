package note.rest.model.dto.request;

import lombok.Data;

@Data
public class CreateNoteRequest {
    private String title;
    private String content;
}
