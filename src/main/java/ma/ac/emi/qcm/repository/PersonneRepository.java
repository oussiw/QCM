package ma.ac.emi.qcm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.emi.qcm.entities.Personne;

public interface PersonneRepository extends JpaRepository<Personne, String> {

}
