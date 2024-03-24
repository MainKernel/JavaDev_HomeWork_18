package note.rest.model.dto.response;

import lombok.Builder;
import lombok.Data;
import note.rest.model.dto.NoteDto;

import java.util.List;

@Data
@Builder
public class GetNotesResponse {
    private Error error;
    private List<NoteDto> notes;

    public enum Error {
        ok,
        error
    }

    public static GetNotesResponse success(List<NoteDto> notes) {
        return builder().error(Error.ok).notes(notes).build();
    }

    public static GetNotesResponse failed(Error error) {
        return builder().error(error).notes(null).build();
    }
}
