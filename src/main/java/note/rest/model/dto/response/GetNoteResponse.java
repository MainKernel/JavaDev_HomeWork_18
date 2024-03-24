package note.rest.model.dto.response;

import lombok.Builder;
import lombok.Data;
import note.rest.model.dto.NoteDto;

@Data
@Builder
public class GetNoteResponse {
    private Error error;
    private NoteDto note;

    public enum Error {
        ok,
        invalidNoteId
    }

    public static GetNoteResponse success(NoteDto note) {
        return builder().error(Error.ok).note(note).build();
    }

    public static GetNoteResponse failed(Error error){
        return builder().error(error).note(null).build();
    }
}
