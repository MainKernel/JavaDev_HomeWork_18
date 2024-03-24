package note.rest.model.dto.response;

import lombok.Builder;
import lombok.Data;
import note.rest.model.dto.NoteDto;

@Data
@Builder
public class PatchNoteResponse {
    private Error error;
    private NoteDto note;

    public static PatchNoteResponse success(NoteDto note) {
        return builder().error(Error.ok).note(note).build();
    }

    public static PatchNoteResponse failed(Error error) {
        return builder().error(error).note(null).build();
    }

    public enum Error {
        ok,
        updateNoteError
    }
}
