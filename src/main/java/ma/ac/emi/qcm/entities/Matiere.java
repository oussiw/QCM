package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Matiere {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	@ManyToMany(mappedBy = "matieres")
	private List<Formateur> formateurs = new ArrayList<Formateur>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "niveau_id", nullable = false)
	private Niveau niveau;
	@OneToMany(mappedBy = "matiere")
	private List<Theme> themes = new ArrayList<Theme>();
	@OneToMany(mappedBy = "matiere")
	private List<QCM> examens = new ArrayList<QCM>();

	public Matiere() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Matiere(String nom, Niveau niveau) {
		super();
		this.nom = nom;
		this.niveau = niveau;
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

	public List<Formateur> getFormateurs() {
		return formateurs;
	}

	public void setFormateurs(List<Formateur> formateurs) {
		this.formateurs = formateurs;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public List<QCM> getExamens() {
		return examens;
	}

	public void setExamens(List<QCM> examens) {
		this.examens = examens;
	}

	@Override
	public String toString() {
		return "Matiere [id=" + id + ", nom=" + nom + ", formateurs=" + formateurs + ", niveau=" + niveau + ", themes="
				+ themes + ", examens=" + examens + "]";
	}

	public void addFormateur(Formateur formateur){
		formateurs.add(formateur);
	}

}
