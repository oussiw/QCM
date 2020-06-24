package ma.ac.emi.qcm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Theme;

public interface QuestionRepository  extends JpaRepository<Question,Long> {

    @Query("select q from Question q where q.id=:x")
    Question findByID(@Param("x")Long idQuestion);

    @Query(" select q from Question q where q.theme=:x")
    Page<Question> listQuestions(@Param("x") Theme theme, Pageable pageable);

}
