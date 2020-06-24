package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer note;
	@ManyToOne
	@JoinColumn(name = "Eleve_id", nullable = false)
	private Eleve eleve;
	@OneToMany
	@JoinTable(name = "note_question")
	private List<Question> questions = new ArrayList<Question>();
	@OneToOne
	private QCM qcm;

	public Note() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Note(Integer note, Eleve eleve, QCM qcm) {
		super();
		this.note = note;
		this.eleve = eleve;
		this.qcm = qcm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public QCM getQcm() {
		return qcm;
	}

	public void setQcm(QCM qcm) {
		this.qcm = qcm;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", note=" + note + ", eleve=" + eleve + ", questions=" + questions + ", qcm=" + qcm
				+ "]";
	}

}
