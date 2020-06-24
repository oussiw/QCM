package ma.ac.emi.qcm.controller;

import ma.ac.emi.qcm.entities.Eleve;
import ma.ac.emi.qcm.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/eleves")
public class EleveController {
	@Autowired
	EleveRepository er;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("eleves", er.findAll());
		return "eleves/Eleves";
	}

	@RequestMapping(value = "/addEleve", method = RequestMethod.POST)
	public String addEleve(@ModelAttribute("SpringWeb") Eleve newEleve, Model model) {
		er.save(newEleve);
		model.addAttribute("eleves", er.findAll());
		return "redirect:/eleves/";

	}
}
