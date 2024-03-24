package note.rest.model.dto.request;

import lombok.Data;

@Data
public class PatchNoteRequest {
    private Long id;
    private String title;
    private String content;
}
