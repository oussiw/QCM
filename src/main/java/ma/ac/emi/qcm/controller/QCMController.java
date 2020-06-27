package ma.ac.emi.qcm.controller;

import ma.ac.emi.qcm.entities.*;
import ma.ac.emi.qcm.repository.*;
import ma.ac.emi.qcm.service.QCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
	@Autowired
	EleveRepository eleveRepository;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	NoteRepository noteRepository;

	@GetMapping("/list")
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

	@GetMapping("/")
	public String afficherChoix(Model model,
								@RequestParam(name = "classe_id",defaultValue = "-1") Long classe_id,
								@RequestParam(name = "matiere_id",defaultValue = "-1") Long matiere_id){

		Eleve eleve = eleveRepository.getOne("oussama");

		model.addAttribute("eleve",eleve);

		if(matiere_id >=0){
			Matiere matiereA = matiereRepository.getOne(matiere_id);
			model.addAttribute("matiereA", matiereA);
		}
		else {
			Matiere matiere = new Matiere();
			model.addAttribute("matiereA", matiere);
		}

		if(classe_id >=0)
			model.addAttribute("classeA",classeRepository.getOne(classe_id));
		else{
			Classe classe = new Classe();
			model.addAttribute("classeA",classe);
		}
		if(classe_id >=0 && matiere_id >=0){
			QCM qcm = new QCM();
			List<Mode> modes = new ArrayList<Mode>(EnumSet.allOf(Mode.class));
			qcm.setClasse(classeRepository.getOne(classe_id));
			qcm.setMatiere(matiereRepository.getOne(matiere_id));
			model.addAttribute("QCM",qcm);
			model.addAttribute("modes",modes);
		}
		return "qcms/choisirQcm";
	}

	@PostMapping("/getClasse")
	public String classeChoisie(@ModelAttribute("classeA")Classe classe){
		return "redirect:/qcms/?classe_id="+classe.getId();
	}

	@PostMapping("/getMatiere")
	public String matiereChoisie(@ModelAttribute("matiereA")Matiere matiere,
								 @RequestParam(name = "classe_id") Long classe_id){
		return "redirect:/qcms/?classe_id="+classe_id+"&matiere_id="+matiere.getId();
	}

	@PostMapping("/genererQcm")
	public String genererQcm(Model model,@ModelAttribute("QCM")QCM qcm,
						   @RequestParam(name = "eleve_id") String login,
						   @RequestParam(name = "classe_id") Long classe_id,
						   @RequestParam(name = "matiere_id") Long matiere_id){

		Eleve eleve = eleveRepository.getOne("oussama");
		Matiere matiere = matiereRepository.getOne(matiere_id);
		Classe classe = classeRepository.getOne(classe_id);
		qcm.setClasse(classe);
		qcm.setMatiere(matiere);
		Mode m = qcm.getMode();
		Boolean b = qcm.isTest();
		Formateur formateur = formateurRepository.findFormateurByClassAndMatiere(classe,matiere).get(0);
		List<QCM> qcms = qcmRepository.findQCMByMatFormModeTest(matiere,formateur,m,b);
		if(qcms.size()>0){
			qcm = qcms.get(0);
			model.addAttribute("QCM",qcm);
			model.addAttribute("eleve",eleve);
//			model.addAttribute("matiereA", matiere);
			model.addAttribute("classeA", classe);
			return "qcms/qcm-form";
		}
		return "/oups";
	}

	@GetMapping("/resultatQcm")
	public String genererQcm(Model model,
							 @RequestParam(name = "note_id") Long note_id,
							 @RequestParam(name = "classe_id") Long classe_id,
							 @RequestParam(name = "matiere_id") Long matiere_id){


		Matiere matiere = matiereRepository.getOne(matiere_id);
		Classe classe = classeRepository.getOne(classe_id);
		Note note = noteRepository.getOne(note_id);
		QCM qcm = note.getQcm();
		List<Question> qs = qcm.getQuestions();
		int scoreMax = 0;
		for(Question q:qs) scoreMax +=q.getBareme();

		Eleve eleve = note.getEleve();
		model.addAttribute("ScoreMax",scoreMax);
		model.addAttribute("NOTE",note);
		model.addAttribute("QCM",qcm);
		model.addAttribute("eleve",eleve);
		model.addAttribute("matiereA", matiere);
		model.addAttribute("classeA", classe);
		return "qcms/qcm-form";
	}

	@PostMapping("/commencerQCM")
	public String commencerQCM(
			@ModelAttribute("QCM")QCM qcm,
			@RequestParam(name = "eleve_id") String login,
			@RequestParam(name = "classe_id") Long classe_id,
			@RequestParam(name = "matiere_id") Long matiere_id){

		Matiere matiere = matiereRepository.getOne(matiere_id);
		Classe classe = classeRepository.getOne(classe_id);
		Formateur formateur = formateurRepository.findFormateurByClassAndMatiere(classe,matiere).get(0);
		QCM qcm1 = new QCM(qcm.getNom().concat('_'+login),qcm.getType(),qcm.getMode(),qcm.isTest(),
				qcm.isPartage(),classe,formateur,matiere,qcm.getConsignes());
		qcmRepository.save(qcm1);
		qcm1.setQuestions(getRandomQuestions(questionRepo.findByQCM(qcm)));
		qcmRepository.save(qcm1);
		Note note = new Note(0,eleveRepository.getOne(login),qcm1);
		noteRepository.save(note);
//		note.setQuestions(qcm1.getQuestions());
//		noteRepository.save(note);
		return "redirect:/questions/afficherQu?classe_id="+classe_id+"&matiere_id="+matiere_id+"&note_id="+note.getId()+"&qcm_id="+qcm1.getId();
	}

	private List<Question> getRandomQuestions(List<Question> questions){
		int max = questions.size();
		int t =  questions.size();
		List<Question> questions1 = new ArrayList<>();
		for(int i=0;i<t;i++){
			if(i<t-1) max--;
			if(i==t-1){
				Question question = questions.get(0);
				questions1.add(question);
				break;
			}
			Random r = new Random();
			Question question = questions.get(getRandomNumberInRange(0,max));
			questions1.add(question);
			questions.remove(question);

		}
		return questions1;
	}

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


}
