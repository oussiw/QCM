package ma.ac.emi.qcm.controller;

import ma.ac.emi.qcm.entities.*;
import ma.ac.emi.qcm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	QuestionRepository questionRepo;
	@Autowired
	ThemeRepository themeRepo;
	@Autowired
	FormateurRepository formateurRepo;
	@Autowired
	FormationRepository formationRepo;
	@Autowired
	NiveauRepository niveauRepo;
	@Autowired
	MatiereRepository matiereRepo;
	@Autowired
	QCMRepository qcmRepository;

	@GetMapping("/")
	public String getQuestions(Model model, @RequestParam(name = "theme_id", defaultValue = "-1") Long themeid,
			@RequestParam(name = "qcm_id", defaultValue = "-1") Long qcmId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		try {
			if (themeid >= 0) {
				Theme theme = themeRepo.getOne(themeid);
				Page<Question> pageQuestions = questionRepo.listQuestions(theme, PageRequest.of(page, size));
				int[] pages = new int[pageQuestions.getTotalPages()];
				model.addAttribute("listQuestion", pageQuestions);
				model.addAttribute("themeid", themeid);
				model.addAttribute("pageCourante", page);
				model.addAttribute("themeid", themeid);
				model.addAttribute("pages", pages);
				model.addAttribute("size", size);
				model.addAttribute("theme", theme);

			} else if (qcmId >= 0) {
				QCM qcm = qcmRepository.getOne(qcmId);
				Page<Question> pageQuestions = new PageImpl<Question>(qcm.getQuestions(), PageRequest.of(page, size),
						qcm.getQuestions().size());
				int[] pages = new int[pageQuestions.getTotalPages()];
				model.addAttribute("listQuestion", pageQuestions);
				model.addAttribute("pageCourante", page);
				model.addAttribute("qcmId", qcmId);
				model.addAttribute("pages", pages);
				model.addAttribute("size", size);
				model.addAttribute("qcm", qcm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "questions/qsts";
	}

	@GetMapping("/afficherReponses")
	public String getReponses(@RequestParam(name = "question_id") Long questionId) {
		return "redirect:/reponses/list?question_id=" + questionId;
	}

	@GetMapping("/add")
	public String showAddForm(Model theModel, @RequestParam(name = "theme_id", defaultValue = "-1") Long themeid,
			@RequestParam(name = "qcm_id", defaultValue = "-1") Long qcmId) {

		Formateur formateur = formateurRepo.getOne("ali");
		if (themeid >= 0) {
			Theme theme = themeRepo.getOne(themeid);
			Question question = new Question();
			question.setTheme(theme);
			question.setFormateur(formateur);
			List<QCM> qcms = qcmRepository.findByMatiere(theme.getMatiere().getId());
			theModel.addAttribute("questionA", question);
			theModel.addAttribute("theme_id", themeid);
			theModel.addAttribute("qcms", qcms);

		} else if (qcmId >= 0) {
			QCM qcm = qcmRepository.getOne(qcmId);
			List<Theme> themes = themeRepo.getThemeByMatiere(qcm.getMatiere());
			Question question = new Question();
			question.getQcms().add(qcm);
			question.setFormateur(formateur);
			qcm.getQuestions().add(question);
			theModel.addAttribute("themes", themes);
			theModel.addAttribute("questionA", question);
			theModel.addAttribute("qcm_id", qcmId);
		}
		return "ajouterquestion";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("Question") Question question,
			@RequestParam(name = "theme_id", defaultValue = "-1") Long themeid,
			@RequestParam(name = "qcm_id", defaultValue = "-1") Long qcmId) {
		questionRepo.save(question);
		for (QCM q : question.getQcms()) {
			q.getQuestions().add(question);
			qcmRepository.save(q);
		}
		if (qcmId >= 0)
			return "redirect:/questions/list?qcm_id=" + qcmId;
		return "redirect:/questions/list?theme_id=" + question.getTheme().getId();
	}

	@GetMapping("/infoqst/{id}")
	public String infqst(Model model, @PathVariable("id") Long idQuestion,
			@RequestParam(name = "theme_id", defaultValue = "-1") Long themeid,
			@RequestParam(name = "qcm_id", defaultValue = "-1") Long qcmId) {
		Question question = questionRepo.findByID(idQuestion);
		model.addAttribute("question", question);
		if (themeid >= 0) {
			model.addAttribute("idtheme", themeid);
		} else if (qcmId >= 0) {
			model.addAttribute("idqcm", qcmId);
		}
		return "EditQuestion";
	}

	@PostMapping("/infoedit")
	public String editqst(@ModelAttribute("Question") Question question,
			@RequestParam(name = "qcm_id", defaultValue = "-1") Long qcmId) {
		questionRepo.save(question);
		if (qcmId >= 0)
			return "redirect:/questions/list?qcm_id=" + qcmId;
		return "redirect:/questions/list?theme_id=" + question.getTheme().getId();
	}

	@GetMapping("/deletequestion/{id}")
	public String deleteQst(@PathVariable("id") Long id) {
		Question question = questionRepo.findByID(id);
		Theme theme = question.getTheme();
		Formateur formateur = question.getFormateur();
		List<QCM> qcms = question.getQcms();
		for (QCM qcm : qcms) {
			qcm.getQuestions().remove(question);
		}
		theme.getQuestions().remove(question);
		formateur.getQuestions().remove(question);
		questionRepo.delete(question);
		return "redirect:/questions/list?theme_id=" + theme.getId();
	}

}
