package ma.ac.emi.qcm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.emi.qcm.entities.Classe;

public interface ClasseRepository extends JpaRepository<Classe,Long> {
    Classe getClasseById(Long id);
}
