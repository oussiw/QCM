package ma.ac.emi.qcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ma.ac.emi.qcm.entities.Niveau;
import ma.ac.emi.qcm.service.NiveauService;

@Controller
@RequestMapping("/niveaux")
public class NiveauController {
	@Autowired
	NiveauService niveauService;

	@RequestMapping("/")
	public String theme(Model model, @RequestParam(defaultValue = "0") int p, @RequestParam(defaultValue = "3") int s,
			@RequestParam(defaultValue = "") String mc) {
		Page<Niveau> pageNiveau = niveauService.getPageNiveau(p, s, mc);
		model.addAttribute("pageNiveau", pageNiveau.getContent());
		model.addAttribute("nbPages", new int[pageNiveau.getTotalPages()]);
		model.addAttribute("p", p);
		model.addAttribute("s", s);
		model.addAttribute("mc", mc);

		model.addAttribute("newNiveau", new Niveau());
		model.addAttribute("listFormation", niveauService.getListFormation());
		return "niveaux/niveau";
	}

	@RequestMapping("/delete")
	public String deleteNiveau(@RequestParam Long id, int p, int s, String mc) {
		niveauService.deleteNiveau(id);
		return "redirect:/niveaux?" + "p=" + p + "&s=" + s + "&mc=" + mc;
	}

	@RequestMapping(value = "/confirmation", method = RequestMethod.POST)
	public String addedDescriptionNiveau(Model model, Niveau niveau, @RequestParam("f_id") Long formation_id) {
		Niveau newNiveau = niveauService.saveNiveau(niveau, formation_id);
		model.addAttribute("newNiveau", newNiveau);
		return "niveaux/niveauConfirmation";
	}

	@RequestMapping("/update")
	public String updateNiveau(Model model, @RequestParam Long id) {
		model.addAttribute("niveau", niveauService.getNiveauById(id));
		model.addAttribute("listFormation", niveauService.getListFormation());
		return "niveaux/niveauUpdate";
	}

}
