package ma.ac.emi.qcm.repository;

import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Reponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReponseRepository extends JpaRepository<Reponse,Long> {

    @Query(" select r from Reponse r where r.question=:x")
    Page<Reponse> listReponses(@Param("x") Question question, Pageable pageable);
}
