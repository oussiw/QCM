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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/questions")
public class QuestionController {


	@Autowired
	EleveRepository eleveRepository;

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
	@Autowired
	ClasseRepository classeRepo;

	@Autowired
	NoteRepository noteRepository;

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

	@GetMapping("/afficherQu")
	public String afficherQu(Model model,
							 @RequestParam(name = "qcm_id") Long qcm_id,
							 @RequestParam(name = "note_id")  Long note_id,
							 @RequestParam(name = "classe_id") Long classe_id,
							 @RequestParam(name = "matiere_id") Long matiere_id,
							 @RequestParam(name = "numero_qst",defaultValue = "1")Integer numero_qst,
							 @RequestParam(name = "total_qst",defaultValue = "1")Integer total_qst){

		QCM qcm = qcmRepository.getOne(qcm_id);
		Question q2 = getRandomQuestion(qcm.getQuestions());
		Reponse reponse = new Reponse();
		reponse.setQuestion(q2);

		System.out.println(">>>>>>>>>>>>>>"+qcm.getNom());
		Matiere matiere = matiereRepo.getOne(matiere_id);
		Classe classe = classeRepo.getOne(classe_id);
		model.addAttribute("question",q2);
		model.addAttribute("reponse",reponse);
		model.addAttribute("note_id",note_id);
		model.addAttribute("matiereA", matiere);
		model.addAttribute("classeA", classe);
		model.addAttribute("index",q2.getId());
		model.addAttribute("qcm",qcm);
		model.addAttribute("numero_qst",numero_qst);
		model.addAttribute("total_qst",total_qst);
		return "questions/question";
	}

	private Question getRandomQuestion(List<Question> questions){
		int max = questions.size()-1;
		Question question = questions.get(getRandomNumberInRange(0,max));
		return question;
	}

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
//			throw new IllegalArgumentException("max must be greater than min");
			return 0;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


	@PostMapping("/traiterQuestion")
	public String traiterQuestion(@ModelAttribute("Reponse")Reponse reponse,
								  @RequestParam(name = "qcm_id") Long qcm_id,
								  @RequestParam(name = "note_id") Long note_id,
								  @RequestParam(name = "classe_id") Long classe_id,
								  @RequestParam(name = "matiere_id") Long matiere_id,
								  @RequestParam(name = "question_id")Long index,
								  @RequestParam(name = "numero_qst",defaultValue = "1")Integer numero_qst,
								  @RequestParam(name = "total_qst",defaultValue = "1")Integer total_qst){

		System.out.println(">>>>>>>>>>>>>>"+reponse.getReponse());
		QCM qcm = qcmRepository.getOne(qcm_id);
		Question question = questionRepo.getOne(index);
		System.out.println(">>>>> Reponse >>>>>>>>>"+question.getEnonce());
		for(Reponse rep:question.getReponses()){
			if(reponse.getReponse().equals(rep.getReponse()) && rep.isCorrecte()){
				Note note = noteRepository.getOne(note_id);
				note.setNote(note.getNote()+question.getBareme());
				noteRepository.save(note);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+note.getNote());
			}
		}
		if(qcm.getQuestions().size()>1) {
			qcm.getQuestions().remove(question);
			qcmRepository.save(qcm);
			numero_qst++;
			return "redirect:/questions/afficherQu?classe_id="+classe_id+"&matiere_id="+matiere_id+"&qcm_id="
					+qcm.getId()+"&note_id="+note_id+"&numero_qst="+numero_qst+"&total_qst="+total_qst;
		}
		qcmRepository.delete(qcm);
		return "redirect:/qcms/resultatQcm?classe_id="+classe_id+"&matiere_id="+matiere_id+"&note_id="+note_id;
	}

}
