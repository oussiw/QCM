package ma.ac.emi.qcm.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String reponse;
	private boolean correcte;

	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	public Reponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reponse(String reponse, boolean correcte, Question question) {
		super();
		this.reponse = reponse;
		this.correcte = correcte;
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public boolean isCorrecte() {
		return correcte;
	}

	public void setCorrecte(boolean correcte) {
		this.correcte = correcte;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Reponse [id=" + id + ", reponse=" + reponse + ", correcte=" + correcte + ", question=" + question + "]";
	}

}
