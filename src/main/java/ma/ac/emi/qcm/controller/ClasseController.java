package ma.ac.emi.qcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ma.ac.emi.qcm.entities.Classe;
import ma.ac.emi.qcm.entities.Eleve;
import ma.ac.emi.qcm.entities.Formateur;
import ma.ac.emi.qcm.repository.ClasseRepository;
import ma.ac.emi.qcm.repository.EleveRepository;
import ma.ac.emi.qcm.repository.FormateurRepository;

@Controller
@RequestMapping("/classes")
public class ClasseController {
	@Autowired
	ClasseRepository cr;
	@Autowired
	EleveRepository er;
	@Autowired
	FormateurRepository fr;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("Classes", cr.findAll());
		model.addAttribute("Eleves", er.findAll());
		model.addAttribute("Formateurs", fr.findAll());
		return "classes/Home";
	}

	@GetMapping(path = "/ajouterClasse")
	public String addClasse(Model model) {
		model.addAttribute("Classe", new Classe());
		return "classes/FormClasse";
	}

	@PostMapping(path = "/saveClasse")
	public String enregistrer(Model model, Classe c) {
		cr.save(c);
		return "redirect:/classes/";
	}

	@GetMapping(path = "/supprimerClasse")
	public String supprimer(Model model, Long id) {
		cr.deleteById(id);
		return "redirect:/classes/";
	}

	@GetMapping(path = "/editerClasse")
	public String editer(Model model, Long id) {
		Classe c = cr.findById(id).get();
		model.addAttribute("Classe", c);
		return "classes/FormClasse";
	}

	@GetMapping(path = "/detailsClasse")
	public String details(Model model, Long id) {
		Classe c = cr.findById(id).get();
		model.addAttribute("Classe", c);
		return "classes/DetailsClasse";
	}

	/*----------------------------------------------------------------------------*/

	@GetMapping(path = "/ajouterEleve")
	public String addEleve(Model model) {
		model.addAttribute("Eleve", new Eleve());
		return "eleves/FormEleve";
	}

	@PostMapping(path = "/saveEleve")
	public String enregistrerE(Model model, Eleve e) {
		er.save(e);
		return "redirect:/classes/";
	}

	@GetMapping(path = "/supprimerEleve")
	public String supprimerE(Model model, String login, Long id) {
		Classe c = cr.findById(id).get();
		c.getEleves().remove(er.findById(login).get());
		cr.save(c);
		return "redirect:/classes/detailsClasse?id=" + id;
	}

	@GetMapping(path = "/editerEleve")
	public String editerE(Model model, String login) {
		Eleve e = er.findById(login).get();
		model.addAttribute("Eleve", e);
		return "eleves/FormEleve";
	}

	/*----------------------------------------------------------------------------*/

	@GetMapping(path = "/ajouterFormateur")
	public String addFormateur(Model model) {
		model.addAttribute("Formateur", new Formateur());
		return "formateurs/FormFormateur";
	}

	@PostMapping(path = "/saveFormateur")
	public String enregistrerF(Model model, Formateur f) {
		fr.save(f);
		return "redirect:/classes/";
	}

	@GetMapping(path = "/supprimerFormateur")
	public String supprimerF(Model model, String login, Long id) {
		Classe c = cr.findById(id).get();
		c.getFormateurs().remove(fr.findById(login).get());
		cr.save(c);
		return "redirect:/classes/detailsClasse?id=" + id;
	}

	@GetMapping(path = "/editerFormateur")
	public String editerF(Model model, String login) {
		Formateur f = fr.findById(login).get();
		model.addAttribute("Formateur", f);
		return "formateurs/FormFormateur";
	}

	/*----------------------------------------------------------------------------*/

	@GetMapping(path = "/attribuerClasseE")
	public String attribuerE(Model model, String login) {
		Eleve e = er.findById(login).get();
		model.addAttribute("Eleve", e);
		model.addAttribute("Classes", cr.findAll());
		return "classes/AttribuerClasse";
	}

	@GetMapping(path = "/attribuerClasseF")
	public String attribuerF(Model model, String login) {
		Formateur f = fr.findById(login).get();
		model.addAttribute("Formateur", f);
		model.addAttribute("Classes", cr.findAll());
		return "classes/AttribuerClasse";
	}

	@GetMapping(path = "/attribuerClasse")
	public String attribuerClasse(Model model, String login, Long cl) {
		Eleve e = er.findById(login).get();
		Classe c = cr.findById(cl).get();
		for (Eleve e1 : c.getEleves()) {
			if (e1.getLogin() == e.getLogin()) {
				model.addAttribute("Exception", "Cet élève est dèja dans cette classe");
				return "erreur";
			}
		}
		c.getEleves().add(e);
		cr.save(c);
		return "redirect:/classes/detailsClasse?id=" + cl;
	}

	@GetMapping(path = "/attribuerClasse1")
	public String attribuerClasse1(Model model, String login, Long cl) {
		Formateur f = fr.findById(login).get();
		Classe c = cr.findById(cl).get();
		for (Formateur f1 : c.getFormateurs()) {
			if (f1.getLogin() == f.getLogin()) {
				model.addAttribute("Exception", "Ce formateur est dèja dans cette classe");
				return "erreur";
			}
		}
		c.getFormateurs().add(f);
		cr.save(c);
		return "redirect:/classes/detailsClasse?id=" + cl;

	}

}
