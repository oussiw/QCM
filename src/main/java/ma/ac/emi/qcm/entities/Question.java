package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String enonce;
	private Integer bareme;
	private Difficulte difficulte;
	private boolean test;
	private boolean partage;
	@ManyToOne
	@JoinColumn(name = "theme_id", nullable = false)
	private Theme theme;

	@ManyToOne
	@JoinColumn(name = "formateur_id", nullable = false)
	private Formateur formateur;
	@OneToMany(mappedBy = "question")
	private List<Reponse> reponses = new ArrayList<Reponse>();
	@ManyToMany(mappedBy = "questions")
	private List<QCM> qcms = new ArrayList<QCM>();

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(String enonce, Integer bareme, Difficulte difficulte, boolean test, boolean partage, Theme theme,
			Formateur formateur) {
		super();
		this.enonce = enonce;
		this.bareme = bareme;
		this.difficulte = difficulte;
		this.test = test;
		this.partage = partage;
		this.theme = theme;
		this.formateur = formateur;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	public Integer getBareme() {
		return bareme;
	}

	public void setBareme(Integer bareme) {
		this.bareme = bareme;
	}

	public Difficulte getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(Difficulte difficulte) {
		this.difficulte = difficulte;
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

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	public List<QCM> getQcms() {
		return qcms;
	}

	public void setQcms(List<QCM> qcms) {
		this.qcms = qcms;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", enonce=" + enonce + ", bareme=" + bareme + ", difficulte=" + difficulte
				+ ", test=" + test + ", partage=" + partage + ", theme=" + theme + ", formateur=" + formateur
				+ ", reponses=" + reponses + ", qcms=" + qcms + "]";
	}

}
