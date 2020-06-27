package ma.ac.emi.qcm.repository;

import ma.ac.emi.qcm.entities.Classe;
import ma.ac.emi.qcm.entities.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.emi.qcm.entities.Formateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormateurRepository extends JpaRepository<Formateur, String>{
    Formateur getFormateurByLogin(String login);

    @Query("select f from Formateur f join f.matieres m join f.classes c where c=:cl and m=:ma")
    List<Formateur> findFormateurByClassAndMatiere(@Param("cl")Classe classe, @Param("ma") Matiere matiere);
}
