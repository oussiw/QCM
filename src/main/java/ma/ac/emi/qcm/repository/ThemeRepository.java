package ma.ac.emi.qcm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.ac.emi.qcm.entities.Matiere;
import ma.ac.emi.qcm.entities.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long>{
	@Query("select t from Theme t where t.nom like:x")
	public Page<Theme> getPageThemeByNom(@Param("x")String mc,Pageable pageable);

    @Query("select t from Theme t where t.matiere=:x")
    List<Theme> getThemeByMatiere(@Param("x") Matiere matiere);

}
