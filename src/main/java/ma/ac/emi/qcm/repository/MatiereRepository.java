package ma.ac.emi.qcm.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.ac.emi.qcm.entities.Matiere;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere,Long>{
	@Query("select m from Matiere m where m.nom like:x")
	Page<Matiere> getPageMatiereByNom(@Param("x")String mc,Pageable pageable);
	
    Matiere getMatiereById(long id);
}
