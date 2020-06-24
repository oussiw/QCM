package ma.ac.emi.qcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ma.ac.emi.qcm.entities.Formation;
import ma.ac.emi.qcm.repository.FormationRepository;

@Controller
@RequestMapping("/formations")
public class FormationController {
	@Autowired
	private FormationRepository formationRepository;

	@GetMapping("/")
	public String Index(Model model, @RequestParam(defaultValue = "0") int p, @RequestParam(defaultValue = "3") int s,
			@RequestParam(defaultValue = "") String mc) {
		Page<Formation> formations = formationRepository.chercherFormation("%" + mc + "%", PageRequest.of(p, s));
		model.addAttribute("pageFormations", formations);
		int pagesCount = formations.getTotalPages();
		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++) {
			pages[i] = i;
		}
		model.addAttribute("nbPages", pages);
		model.addAttribute("p", p);
		model.addAttribute("s", s);
		model.addAttribute("mc", mc);
		model.addAttribute("formation", new Formation());
		return "formations/formation";
	}

	@PostMapping("/saveFormation")
	public String save(Model model, Formation formation) {
		formationRepository.save(formation);
		model.addAttribute("formation", formation);
		return "formations/formationConfirmation";
	}

	@GetMapping("/deleteFormation/{id}")
	public String delete(@PathVariable("id") Long id) {
		formationRepository.delete(formationRepository.findById(id).get());
		return "redirect:/formations/";
	}

	@GetMapping("/editFormation")
	public String formEdit(Model model, Long id) {
		Formation formation = formationRepository.findById(id).get();
		model.addAttribute("formation", formation);
		return "formations/formationUpdate";
	}
}
