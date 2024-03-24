package note.rest.controller;

import lombok.AllArgsConstructor;
import note.rest.model.dto.request.CreateNoteRequest;
import note.rest.model.dto.request.PatchNoteRequest;
import note.rest.model.dto.response.*;
import note.rest.service.NoteService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/notes")
    public GetNotesResponse getAllUserNotes() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return noteService.getAllNotes(name);
    }

    @GetMapping("/notes/{id}")
    public GetNoteResponse getNote(@PathVariable long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return noteService.getNoteResponse(name, id);
    }

    @PostMapping("/notes")
    public CreateNoteResponse createNoteRequest(@RequestBody CreateNoteRequest createNoteRequest) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return noteService.createNote(createNoteRequest, name);
    }

    @DeleteMapping("/notes/{id}")
    public DeleteNoteResponse deleteNote(@PathVariable long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return noteService.deleteNote(name, id);
    }

    @PatchMapping("/notes")
    public PatchNoteResponse patchNote(@RequestBody PatchNoteRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return noteService.updateNote(request, name);
    }
}
