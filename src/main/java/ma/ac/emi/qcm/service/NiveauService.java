package ma.ac.emi.qcm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.ac.emi.qcm.entities.Formation;
import ma.ac.emi.qcm.entities.Niveau;
import ma.ac.emi.qcm.repository.FormationRepository;
import ma.ac.emi.qcm.repository.NiveauRepository;

@Service
public class NiveauService{
	@Autowired
	NiveauRepository niveauRepository;
	@Autowired
	FormationRepository formationRepository;
	
	public List<Niveau> getListNiveau(){
		return niveauRepository.findAll();
	}
	
	public Page<Niveau> getPageNiveau(int p,int s){
		return niveauRepository.findAll(PageRequest.of(p, s));
	}
	
	public Page<Niveau> getPageNiveau(int p,int s, String mc){
		return niveauRepository.getPageNiveauByNom("%"+mc+"%", PageRequest.of(p,s));
	}
	
	public List<Formation> getListFormation(){
		return formationRepository.findAll();
	}
	
	public void deleteNiveau(Long id) {
		niveauRepository.deleteById(id);
	}

	public Niveau saveNiveau(Niveau niveau, Long id) {
		Formation f=formationRepository.findById(id).get();
		niveau.setFormation(f);
		niveauRepository.save(niveau);
		return niveau;
	}
	
	public Niveau getNiveauById(Long id) {
		return niveauRepository.findById(id).get();
	}

}
