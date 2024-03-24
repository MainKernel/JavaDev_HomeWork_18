package note.rest.service;

import lombok.RequiredArgsConstructor;
import note.rest.model.dto.NoteDto;
import note.rest.model.dto.request.CreateNoteRequest;
import note.rest.model.dto.request.PatchNoteRequest;
import note.rest.model.dto.response.*;
import note.rest.model.entitiy.NoteEntity;
import note.rest.model.entitiy.UserEntity;
import note.rest.repository.NoteRepository;
import note.rest.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repository;
    private final UserRepository userRepository;

    public GetNotesResponse getAllNotes(String username) {
        List<NoteDto> collect = repository.findAllByUserEntityUsername(username).stream().map(e -> NoteDto.builder()
                .id(e.getId())
                .userId(e.getUserEntity().getId())
                .title(e.getTitle())
                .content(e.getContent())
                .createdAt(e.getCreatedAt()).build()).toList();

        return GetNotesResponse.success(collect);
    }

    public GetNoteResponse getNoteResponse(String username, Long id) {
        Optional<NoteEntity> byIdAndUserEntityUsername = repository.findByIdAndUserEntityUsername(id, username);
        if (byIdAndUserEntityUsername.isPresent()) {
            return GetNoteResponse.success(byIdAndUserEntityUsername.stream().map(e -> NoteDto.builder()
                    .id(e.getId())
                    .userId(e.getUserEntity().getId())
                    .title(e.getTitle())
                    .content(e.getContent())
                    .createdAt(e.getCreatedAt()).build()).findAny().get());
        }
        return GetNoteResponse.failed(GetNoteResponse.Error.invalidNoteId);
    }

    public CreateNoteResponse createNote(CreateNoteRequest request, String username) {
        if(request.getTitle().isEmpty() || request.getTitle().isBlank()){
            return CreateNoteResponse.failed(CreateNoteResponse.Error.invalidTitle);
        } else if(request.getContent().isEmpty() || request.getContent().isBlank()){
            return CreateNoteResponse.failed(CreateNoteResponse.Error.invalidContent);
        }
        try {
            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            NoteEntity save = repository.save(NoteEntity.builder()
                    .userEntity(user)
                    .title(request.getTitle())
                    .content(request.getContent())
                    .build());
            return CreateNoteResponse.success(NoteDto.builder()
                    .id(save.getId())
                    .userId(save.getUserEntity().getId())
                    .title(save.getTitle())
                    .content(save.getContent())
                    .createdAt(save.getCreatedAt())
                    .build());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }

        return CreateNoteResponse.failed(CreateNoteResponse.Error.serverError);
    }

    public DeleteNoteResponse deleteNote(String username, Long id) {
        Optional<NoteEntity> byIdAndUserEntityUsername = repository.findByIdAndUserEntityUsername(id, username);
        if (byIdAndUserEntityUsername.isPresent()) {
            repository.deleteById(id);
            return DeleteNoteResponse.success();
        }
        return DeleteNoteResponse.failed(DeleteNoteResponse.Error.userNoteWithIdNotFound);
    }

    public PatchNoteResponse updateNote(PatchNoteRequest request, String username) {
        Optional<NoteEntity> byIdAndUserEntityUsername = repository
                .findByIdAndUserEntityUsername(request.getId(), username);

        if (byIdAndUserEntityUsername.isPresent() &&
        !request.getContent().isBlank() && !request.getContent().isEmpty()
                && !request.getTitle().isEmpty() && !request.getTitle().isBlank()) {
            NoteEntity noteEntity = byIdAndUserEntityUsername.get();
            noteEntity.setTitle(request.getTitle());
            noteEntity.setContent(request.getContent());
            repository.save(noteEntity);
            return PatchNoteResponse.success(NoteDto.builder()
                    .id(noteEntity.getId())
                    .userId(noteEntity.getUserEntity().getId())
                    .title(noteEntity.getTitle())
                    .content(noteEntity.getContent())
                    .createdAt(noteEntity.getCreatedAt())
                    .build());
        }
        return PatchNoteResponse.failed(PatchNoteResponse.Error.updateNoteError);
    }
}
