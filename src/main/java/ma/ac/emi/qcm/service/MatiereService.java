package ma.ac.emi.qcm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.ac.emi.qcm.entities.Matiere;
import ma.ac.emi.qcm.entities.Niveau;
import ma.ac.emi.qcm.repository.MatiereRepository;
import ma.ac.emi.qcm.repository.NiveauRepository;

@Service
public class MatiereService{
	@Autowired
	MatiereRepository matiereRepository;
	@Autowired
	NiveauRepository niveauRepository;
	
	public List<Matiere> getListMatiere(){
		return matiereRepository.findAll();
	}
	
	public Page<Matiere> getPageMatiere(int p,int s){
		return matiereRepository.findAll(PageRequest.of(p, s));
	}
	
	public Page<Matiere> getPageMatiere(int p,int s, String mc){
		return matiereRepository.getPageMatiereByNom("%"+mc+"%", PageRequest.of(p,s));
	}
	
	public List<Niveau> getListNiveau(){
		return niveauRepository.findAll();
	}
	
	public void deleteMatiere(Long id) {
		matiereRepository.deleteById(id);
	}

	public Matiere saveMatiere(Matiere matiere, Long id) {
		Niveau n=niveauRepository.findById(id).get();
		matiere.setNiveau(n);
		matiereRepository.save(matiere);
		return matiere;
	}
	
	public Matiere getMatiereById(Long id) {
		return matiereRepository.findById(id).get();
	}

}
