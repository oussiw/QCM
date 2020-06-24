package ma.ac.emi.qcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ma.ac.emi.qcm.entities.Eleve;
import ma.ac.emi.qcm.entities.Note;
import ma.ac.emi.qcm.entities.QCM;
import ma.ac.emi.qcm.repository.EleveRepository;
import ma.ac.emi.qcm.repository.NoteRepository;
import ma.ac.emi.qcm.repository.QCMRepository;

@Controller
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private NoteRepository nr;
	@Autowired
	private EleveRepository er;
	@Autowired
	private QCMRepository qr;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("notes", nr.findAll());
		model.addAttribute("eleves", er.findAll());
		model.addAttribute("qcms", qr.findAll());
		return "Notes";
	}

	@RequestMapping(value = "/addNote", method = RequestMethod.POST)
	public String addNote(int eleve, Long qcm, Integer note, Model model) {
		Eleve eleveFind = null;
		for (Eleve e : er.findAll()) {
			if (e.getMatricule() == eleve) {
				eleveFind = e;
			}
		}
		QCM qcmFind = qr.findById(qcm).get();

		Note newNote = new Note(note, eleveFind, qcmFind);

		nr.save(newNote);
		model.addAttribute("notes", nr.findAll());
		return "redirect:/notes";

	}
}
