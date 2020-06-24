package ma.ac.emi.qcm.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.ac.emi.qcm.entities.Niveau;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau,Long>{
	@Query("select n from Niveau n where n.nom like:x")
	public Page<Niveau> getPageNiveauByNom(@Param("x")String mc,Pageable pageable);

    @Query("select n from Niveau n JOIN n.formation as f where f.id=:x")
    List<Niveau> findByFormation(@Param("x") Long id);
}
