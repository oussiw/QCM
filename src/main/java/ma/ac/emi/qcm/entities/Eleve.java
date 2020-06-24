package ma.ac.emi.qcm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ROLE_ELEVE")
public class Eleve extends Personne {

	private int matricule;

	@ManyToMany(mappedBy = "eleves")
	private List<Classe> classes = new ArrayList<Classe>();
	@OneToMany(mappedBy = "eleve")
	private List<Note> notes = new ArrayList<Note>();

	public Eleve() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Eleve(String nom, String email, String login, String password) {
		super(nom, email, login, password);
		// TODO Auto-generated constructor stub
	}

	public Eleve(String nom, String email, String login, String password, int matricule) {
		super(nom, email, login, password);
		this.matricule = matricule;
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

}
