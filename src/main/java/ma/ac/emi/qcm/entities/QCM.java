package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class QCM {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nom;
	@ElementCollection
	private List<String> consignes;
	private Type type;
	private Mode mode;
	private boolean test;
	private boolean partage;

	@ManyToOne
	@JoinColumn(name = "classe_id", nullable = false)
	private Classe classe;
	@ManyToOne
	@JoinColumn(name = "formateur_id", nullable = false)
	private Formateur formateur;

	@ManyToMany
	@JoinTable(name = "qcms_questions", joinColumns = {
			@JoinColumn(name = "qcm_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Question> questions = new ArrayList<Question>();

	@ManyToOne
	@JoinColumn(name = "matiere_id", nullable = false)
	private Matiere matiere;

	public QCM() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QCM(String nom, Type type, Mode mode, boolean test, boolean partage, Classe classe, Formateur formateur,
			Matiere matiere, List<String> consignes) {
		super();
		this.nom = nom;
		this.type = type;
		this.mode = mode;
		this.test = test;
		this.partage = partage;
		this.classe = classe;
		this.formateur = formateur;
		this.matiere = matiere;
		this.consignes = consignes;
	}
	public QCM(String nom, Type type, Mode mode, boolean test, boolean partage, Classe classe, Formateur formateur,
			   Matiere matiere) {
		super();
		this.nom = nom;
		this.type = type;
		this.mode = mode;
		this.test = test;
		this.partage = partage;
		this.classe = classe;
		this.formateur = formateur;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public boolean isPartage() {
		return partage;
	}

	public void setPartage(boolean partage) {
		this.partage = partage;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public List<String> getConsignes() {
		return consignes;
	}

	public void setConsignes(List<String> consignes) {
		this.consignes = consignes;
	}

	@Override
	public String toString() {
		return "Qcm [id=" + id + ", nom=" + nom + ", type=" + type + ", mode=" + mode + ", test=" + test + ", partage="
				+ partage + ", classe=" + classe + ", formateur=" + formateur + ", questions=" + questions
				+ ", matiere=" + matiere + "]";
	}



}
