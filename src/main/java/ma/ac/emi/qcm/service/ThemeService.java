package ma.ac.emi.qcm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ma.ac.emi.qcm.entities.Matiere;
import ma.ac.emi.qcm.entities.Theme;
import ma.ac.emi.qcm.repository.MatiereRepository;
import ma.ac.emi.qcm.repository.ThemeRepository;

@Service
public class ThemeService{
	@Autowired
	ThemeRepository themeRepository;
	@Autowired
	MatiereRepository matiereRepository;
	
	public List<Theme> getListTheme(){
		return themeRepository.findAll();
	}
	
	public Page<Theme> getPageTheme(int p,int s){
		return themeRepository.findAll(PageRequest.of(p, s));
	}
	
	public Page<Theme> getPageTheme(int p,int s, String mc){
		return themeRepository.getPageThemeByNom("%"+mc+"%", PageRequest.of(p,s));
	}
	
	public List<Matiere> getListMatiere(){
		return matiereRepository.findAll();
	}
	
	public void deleteTheme(Long id) {
		themeRepository.deleteById(id);
	}

	public Theme saveTheme(Theme theme, Long id) {
		Matiere m=matiereRepository.findById(id).get();
		theme.setMatiere(m);
		themeRepository.save(theme);
		return theme;
	}
	
	public Theme getThemeById(Long id) {
		return themeRepository.findById(id).get();
	}

}
