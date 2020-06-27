package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ROLE_Formateur")

public class Formateur extends Personne {

	@ManyToMany
	@JoinTable(name = "classes_formateurs",
			joinColumns = {@JoinColumn(name = "formateur_id", referencedColumnName = "login", nullable = false, updatable = false) },
			inverseJoinColumns = {@JoinColumn(name = "classe_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Classe> classes = new ArrayList<Classe>();

	@OneToMany(mappedBy = "formateur")
	private List<QCM> qcms = new ArrayList<QCM>();

	@ManyToMany
	@JoinTable(name = "formateurs_matieres", joinColumns = {
			@JoinColumn(name = "formateur_id", referencedColumnName = "login", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "matiere_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Matiere> matieres = new ArrayList<Matiere>();
	@OneToMany(mappedBy = "formateur")
	private List<Question> questions;

	public Formateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Formateur(String nom, String email, String login, String password) {
		super(nom, email, login, password);
		// TODO Auto-generated constructor stub
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public List<QCM> getQcms() {
		return qcms;
	}

	public void setQcms(List<QCM> qcms) {
		this.qcms = qcms;
	}

	public List<Matiere> getMatieres() {
		return matieres;
	}

	public void setMatieres(List<Matiere> matieres) {
		this.matieres = matieres;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Formateur [ toString()=" + super.toString() + "]";
	}

	public void addClass(Classe classe){
		classes.add(classe);
	}

	public void addMatiere(Matiere matiere){
		matieres.add(matiere);
	}
}
