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
public class Niveau {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nom;
	@OneToMany(mappedBy = "niveau")
	private List<Classe> classes = new ArrayList<Classe>();
	@OneToMany(mappedBy = "niveau")
	private List<Matiere> matieres = new ArrayList<Matiere>();
	@ManyToOne
	@JoinColumn(name = "formation_id", nullable = false)
	private Formation formation;

	public Niveau(String nom, Formation formation) {
		super();
		this.nom = nom;
		this.formation = formation;
	}

	public Niveau() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public List<Matiere> getMatieres() {
		return matieres;
	}

	public void setMatieres(List<Matiere> matieres) {
		this.matieres = matieres;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return "Niveau [id=" + id + ", classes=" + classes + ", matieres=" + matieres + ", formation=" + formation
				+ "]";
	}

}
