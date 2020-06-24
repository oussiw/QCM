package ma.ac.emi.qcm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import ma.ac.emi.qcm.entities.Eleve;
import ma.ac.emi.qcm.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{

	List<Note> findByEleve(Eleve eleve);
	Optional<Note> findById(Long id);
	

}
