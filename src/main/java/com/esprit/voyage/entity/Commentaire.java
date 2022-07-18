package com.esprit.voyage.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_commentaire")
public class Commentaire implements Serializable {
	private static final long serialVersionUID = 6711457437559348053L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identifiant")
	int identifiant;

	@Column(name = "contenu")
	String contenu;

	@Column(name = "nombre_like")
	int nombreLike;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_de_modification")
	Date dateDeModification;

	@ManyToOne
	@JoinColumn(name = "auteur_id", nullable = false)
	private Client auteur;

	@ManyToOne
	@JoinColumn(name = "publication_id", nullable = false)
	@JsonIgnore
	private Publication publicationId;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_de_creation")
	Date dateDeCreation;

	@PrePersist
	public void prePersist() {
		if (dateDeCreation == null) {
			dateDeCreation = new Date();
		}
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public int getNombreLike() {
		return nombreLike;
	}

	public void setNombreLike(int nombreLike) {
		this.nombreLike = nombreLike;
	}

	public Date getDateDeCreation() {
		return dateDeCreation;
	}

	public void setDateDeCreation(Date dateDeCreation) {
		this.dateDeCreation = dateDeCreation;
	}

	public Client getAuteur() {
		return auteur;
	}

	public void setAuteur(Client auteur) {
		this.auteur = auteur;
	}

	public Publication getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Publication publicationId) {
		this.publicationId = publicationId;
	}

	public Date getDateDeModification() {
		return dateDeModification;
	}

	public void setDateDeModification(Date dateDeModification) {
		this.dateDeModification = dateDeModification;
	}

}
