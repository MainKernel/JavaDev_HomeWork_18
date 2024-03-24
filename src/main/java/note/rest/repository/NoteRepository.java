package note.rest.repository;


import note.rest.model.entitiy.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findAllByUserEntityUsername(String username);

    Optional<NoteEntity> findByIdAndUserEntityUsername(Long id, String username);
}
