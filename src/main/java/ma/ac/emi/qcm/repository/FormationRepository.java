package ma.ac.emi.qcm.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.ac.emi.qcm.entities.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation,Long>{
    public Page<Formation> findByNom(String nom, Pageable page);
    @Query("select f from Formation f where f.nom like :x")
    public Page<Formation> chercherFormation(@Param("x") String mc, Pageable pageable);
}
