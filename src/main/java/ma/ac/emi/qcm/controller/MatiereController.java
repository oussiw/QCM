package ma.ac.emi.qcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ma.ac.emi.qcm.entities.Matiere;
import ma.ac.emi.qcm.service.MatiereService;

@Controller
@RequestMapping("/matieres")
public class MatiereController {
	@Autowired
	MatiereService matiereService;

	@RequestMapping("/")
	public String matiere(Model model, @RequestParam(defaultValue = "0") int p, @RequestParam(defaultValue = "3") int s,
			@RequestParam(defaultValue = "") String mc) {
		Page<Matiere> pageMatiere = matiereService.getPageMatiere(p, s, mc);
		model.addAttribute("pageMatiere", pageMatiere.getContent());
		model.addAttribute("nbPages", new int[pageMatiere.getTotalPages()]);
		model.addAttribute("p", p);
		model.addAttribute("s", s);
		model.addAttribute("mc", mc);

		model.addAttribute("newMatiere", new Matiere());
		model.addAttribute("listNiveau", matiereService.getListNiveau());
		return "matieres/matiere";
	}

	@RequestMapping("/delete")
	public String deleteMatiere(@RequestParam Long id, int p, int s, String mc) {
		matiereService.deleteMatiere(id);
		return "redirect:/matieres/?" + "p=" + p + "&s=" + s + "&mc=" + mc;
	}

	@RequestMapping(value = "/confirmation", method = RequestMethod.POST)
	public String addedDescriptionMatiere(Model model, Matiere matiere, @RequestParam("n_id") Long niveau_id) {
		Matiere newMatiere = matiereService.saveMatiere(matiere, niveau_id);
		model.addAttribute("newMatiere", newMatiere);
		return "matieres/matiereConfirmation";
	}

	@RequestMapping("/update")
	public String updateMatiere(Model model, @RequestParam Long id) {
		model.addAttribute("matiere", matiereService.getMatiereById(id));
		model.addAttribute("listNiveau", matiereService.getListNiveau());
		return "matieres/matiereUpdate";
	}

}
