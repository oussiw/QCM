package ma.ac.emi.qcm.controller;

import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Reponse;
import ma.ac.emi.qcm.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reponses")
public class ReponseController {

	@Autowired
	private ReponseService reponseService;

	@GetMapping("/")
	public String getReponses(Model model, @RequestParam(name = "question_id") Long questionId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		try {
			Question question = reponseService.getQuestion(questionId);
			Page<Reponse> pageReponses = reponseService.listReponseForQuestion(questionId, page, size);
			int[] pages = new int[pageReponses.getTotalPages()];
			model.addAttribute("listReponses", pageReponses);
			model.addAttribute("pageCourante", page);
			model.addAttribute("pages", pages);
			model.addAttribute("size", size);
			model.addAttribute("question", question);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reponses";
	}

	@GetMapping("/add")
	public String addReponse(Model theModel, @RequestParam("questionId") Long questionId) {
		Reponse reponse = new Reponse();
		reponse.setQuestion(reponseService.getQuestion(questionId));
		theModel.addAttribute("reponseAdd", reponse);
		return "add-reponse-form";
	}

	@GetMapping("/update/{id}")
	public ModelAndView updateReponse(@PathVariable(name = "id") Long reponseId) {
		ModelAndView modelAndView = new ModelAndView("update-reponse-form");
		Reponse reponse = reponseService.afficherReponse(reponseId);
		System.out.println(reponse.toString());
		modelAndView.addObject("reponseUpd", reponse);
		return modelAndView;
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("Reponse") Reponse reponse) {
		reponseService.ajouterReponse(reponse);
		return "redirect:/reponses/list?question_id=" + reponse.getQuestion().getId();
	}

	@GetMapping("/delete/{id}")
	public String deleteReponse(@PathVariable(name = "id") Long reponseId) {
		Long questionId = reponseService.afficherReponse(reponseId).getQuestion().getId();
		reponseService.supprimerReponse(reponseId);
		return "redirect:/reponses/list?question_id=" + questionId;
	}
}
