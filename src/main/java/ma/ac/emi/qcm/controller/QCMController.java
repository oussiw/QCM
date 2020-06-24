package ma.ac.emi.qcm.controller;

import ma.ac.emi.qcm.entities.Classe;
import ma.ac.emi.qcm.entities.Formateur;
import ma.ac.emi.qcm.entities.Matiere;
import ma.ac.emi.qcm.entities.QCM;
import ma.ac.emi.qcm.repository.ClasseRepository;
import ma.ac.emi.qcm.repository.FormateurRepository;
import ma.ac.emi.qcm.repository.MatiereRepository;
import ma.ac.emi.qcm.repository.QCMRepository;
import ma.ac.emi.qcm.service.QCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/qcms")
public class QCMController {

	@Autowired
	private QCMService qcmService;

	@Autowired
	private FormateurRepository formateurRepository;

	@Autowired
	private QCMRepository qcmRepository;

	@Autowired
	private MatiereRepository matiereRepository;

	@Autowired
	private ClasseRepository classeRepository;

	@GetMapping("/")
	public String listQcm(Model model, @RequestParam(name = "formateur_id", defaultValue = "") String formateurLogin,
			@RequestParam(name = "matiere_id", defaultValue = "-1") Long matiereid,
			@RequestParam(name = "classe_id", defaultValue = "-1") Long classid) {

		if (matiereid != -1) {
			Matiere matiere = matiereRepository.getMatiereById(matiereid);
			model.addAttribute("liste", qcmRepository.getQcmByMatiere(matiere));

			model.addAttribute("matiere", matiereid);
			model.addAttribute("formateur", formateurLogin);
			model.addAttribute("classe", classid);
		} else if (classid != -1) {
			Classe classe = classeRepository.getClasseById(classid);
			model.addAttribute("liste", qcmRepository.getQcmByClasse(classe));

			model.addAttribute("matiere", matiereid);
			model.addAttribute("formateur", formateurLogin);
			model.addAttribute("classe", classid);
		} else if (formateurLogin != "") {
			Formateur formateur = formateurRepository.getFormateurByLogin(formateurLogin);
			model.addAttribute("liste", qcmRepository.getQcmByFormateur(formateur));

			model.addAttribute("matiere", matiereid);
			model.addAttribute("formateur", formateurLogin);
			model.addAttribute("classe", classid);
		}

		return "qcms/listeQcm";

	}

	@RequestMapping(value = "/addQcm", method = RequestMethod.POST)
	public String addQcm(@ModelAttribute("SpringWeb") QCM newQcm, Model model1) {

		qcmService.saveQcm(newQcm);
		model1.addAttribute("qcm", newQcm);

		return "redirect:/Listqcm";

	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		QCM qcm = qcmService.getQcmById(id);
		model.addAttribute("qcm", qcm);
		return "upadateQcm";
	}

	@RequestMapping("/updateQcm/{id}")
	public String updateQcm(@PathVariable("id") long id, @Valid QCM qcm) {
		qcmService.saveQcm(qcm);
		return "redirect:/Listqcm";

	}

	@RequestMapping("/deleteQcm/{id}")
	public String deleteQcm(@PathVariable("id") long id) {

		qcmService.deleteQcm(id);
		return "redirect:/Listqcm";

	}

}
