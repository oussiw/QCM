package ma.ac.emi.qcm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.ac.emi.qcm.entities.Classe;
import ma.ac.emi.qcm.entities.Formateur;
import ma.ac.emi.qcm.entities.Matiere;
import ma.ac.emi.qcm.entities.QCM;

public interface QCMRepository extends JpaRepository<QCM,Long> {

    @Query("select qcm from QCM qcm where qcm.matiere.id=:x")
    List<QCM> findByMatiere(@Param("x")Long idMatiere);

    QCM getQcmById(long id);

    @Query("select q from QCM q where q.matiere=:x")
    List<QCM> getQcmByMatiere(@Param("x") Matiere matiere);

    @Query("select q from QCM q where q.classe=:x")
    List<QCM> getQcmByClasse(@Param("x") Classe classe);

    @Query("select q from QCM q where q.formateur=:x")
    List<QCM> getQcmByFormateur(@Param("x") Formateur formateur);


}
