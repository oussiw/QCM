package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Classe {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nom;

	@ManyToMany
	@JoinTable(name = "classes_eleves", joinColumns = {
			@JoinColumn(name = "classe_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "eleve_id", referencedColumnName = "login", nullable = false, updatable = false) })
	private List<Eleve> eleves = new ArrayList<Eleve>();
	@ManyToMany
	@JoinTable(name = "classes_formateurs", joinColumns = {
			@JoinColumn(name = "classe_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "formateur_id", referencedColumnName = "login", nullable = false, updatable = false) })
	private List<Formateur> formateurs = new ArrayList<Formateur>();
	@ManyToOne
	private Niveau niveau;
	@OneToMany(mappedBy = "classe")
	private List<QCM> qcms = new ArrayList<QCM>();

	public Classe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Classe(String nom, Niveau niveau) {
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

	public List<Eleve> getEleves() {
		return eleves;
	}

	public void setEleves(List<Eleve> eleves) {
		this.eleves = eleves;
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

	public List<QCM> getQcms() {
		return qcms;
	}

	public void setQcms(List<QCM> qcms) {
		this.qcms = qcms;
	}

}
