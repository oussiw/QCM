package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	@ManyToOne
	@JoinColumn(name = "matiere_id", nullable = false)
	private Matiere matiere;

	@OneToMany(mappedBy = "theme")
	private List<Question> questions = new ArrayList<Question>();

	public Theme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Theme(String nom, Matiere matiere) {
		super();
		this.nom = nom;
		this.matiere = matiere;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Theme [id=" + id + ", nom=" + nom + ", matiere=" + matiere + ", questions=" + questions + "]";
	}

}
