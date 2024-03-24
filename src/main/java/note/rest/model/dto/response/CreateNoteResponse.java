package note.rest.model.dto.response;

import lombok.Builder;
import lombok.Data;
import note.rest.model.dto.NoteDto;

@Data
@Builder
public class CreateNoteResponse {
    private Error error;
    private NoteDto note;

    public static CreateNoteResponse success(NoteDto note) {
        return builder().error(Error.ok).note(note).build();
    }

    public static CreateNoteResponse failed(Error error) {
        return builder().error(error).note(null).build();
    }

    public enum Error {
        ok,
        invalidTitle,
        invalidContent,
        serverError
    }

}
