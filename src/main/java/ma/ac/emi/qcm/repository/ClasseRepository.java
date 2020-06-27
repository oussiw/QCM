package ma.ac.emi.qcm.repository;

import ma.ac.emi.qcm.entities.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.emi.qcm.entities.Classe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe,Long> {
    Classe getClasseById(Long id);


    @Query("select c from Classe c where c.id=:x")
    List<Classe> getClassesByEleve(@Param("x")Eleve eleve);
}
