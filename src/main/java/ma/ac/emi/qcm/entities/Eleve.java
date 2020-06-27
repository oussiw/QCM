package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ROLE_ELEVE")
public class Eleve extends Personne {

	private int matricule;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "classes_eleves", joinColumns = {
			@JoinColumn(name = "eleve_id", referencedColumnName = "login", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "classe_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Classe> classes ;


	@OneToMany(mappedBy = "eleve")
	private List<Note> notes = new ArrayList<Note>();

	public Eleve() {
		super();
		classes= new ArrayList<Classe>();
		// TODO Auto-generated constructor stub
	}

	public Eleve(String nom, String email, String login, String password) {
		super(nom, email, login, password);
		classes= new ArrayList<Classe>();
		// TODO Auto-generated constructor stub
	}

	public Eleve(String nom, String email, String login, String password, int matricule) {
		super(nom, email, login, password);
		this.matricule = matricule;
		classes= new ArrayList<Classe>();
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public int getMatricule() {
		return matricule;
	}

	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}

	@Override
	public String toString() {
		return "Eleve [matricule=" + matricule + ", getMatricule()=" + getMatricule() + ", getNom()=" + getNom()
				+ ", getEmail()=" + getEmail() + ", getLogin()=" + getLogin() + ", getPassword()=" + getPassword()
				+ "]";
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void addClasse(Classe classe){
		classes.add(classe);
	}

}
