package com.esprit.voyage.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "model_voyage")
public class Voyage {

	private static final long serialVersionUID = 671145743755934805L; 
	@Id
	@GeneratedValue
	private int id;
	@NotNull(message = "Decription can not be null")
	@Size(min = 3, max = 100, message = "Decription size must be between 3 and 1500 characters" )
	private String description;
	private Date dateDepart;
	private Date dateArrivee;
	private String lieuDepart;
	private String lieuArrivee;
	private String compagnie;
	private double tarif ;
	private int idClient;

	@Enumerated(EnumType.STRING)
	private ObjectifEnum objectif;
	
	@OneToMany(mappedBy = "voyageId")
	private Set<Publication> publication;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateDepart() {
		return dateDepart;
	}
	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}
	public Date getDateArrivee() {
		return dateArrivee;
	}
	public void setDateArrivee(Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}
	public String getLieuDepart() {
		return lieuDepart;
	}
	public void setLieuDepart(String lieuDepart) {
		this.lieuDepart = lieuDepart;
	}
	public String getLieuArrivee() {
		return lieuArrivee;
	}
	public void setLieuArrivee(String lieuArrivee) {
		this.lieuArrivee = lieuArrivee;
	}
	public ObjectifEnum getObjectif() {
		return objectif;
	}
	public void setObjectif(ObjectifEnum objectif) {
		this.objectif = objectif;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	
	public Voyage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCompagnie() {
		return compagnie;
	}
	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}
	public double getTarif() {
		return tarif;
	}
	public void setTarif(double tarif) {
		this.tarif = tarif;
	}
	public Voyage(int id,
			@NotNull(message = "Decription can not be null") @Size(min = 3, max = 100, message = "Decription size must be between 3 and 1500 characters") String description,
			Date dateDepart, Date dateArrivee, String lieuDepart, String lieuArrivee, String compagnie, double tarif,
			ObjectifEnum objectif) {
		super();
		this.id = id;
		this.description = description;
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.lieuDepart = lieuDepart;
		this.lieuArrivee = lieuArrivee;
		this.compagnie = compagnie;
		this.tarif = tarif;
		this.objectif = objectif;
	}
	
	
}
