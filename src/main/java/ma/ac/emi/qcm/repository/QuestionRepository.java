package ma.ac.emi.qcm.repository;

import ma.ac.emi.qcm.entities.Formateur;
import ma.ac.emi.qcm.entities.QCM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Theme;

import java.text.Format;
import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question,Long> {

    @Query("select q from Question q where q.id=:x")
    Question findByID(@Param("x")Long idQuestion);

    @Query(" select q from Question q where q.theme=:x")
    Page<Question> listQuestions(@Param("x") Theme theme, Pageable pageable);

    @Query("select distinct q from Question q where q.formateur=:for and q.theme=:th")
    List<Question> findByThemeandAndFormateur(@Param("th")Theme theme, @Param("for")Formateur formateur);

    @Query("select distinct q from Question q join q.qcms qc where qc=:qcm")
    List<Question> findByQCM(@Param("qcm")QCM qcm);

}
