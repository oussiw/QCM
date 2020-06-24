package ma.ac.emi.qcm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.emi.qcm.entities.Formateur;

public interface FormateurRepository extends JpaRepository<Formateur, String>{
    Formateur getFormateurByLogin(String login);
}
