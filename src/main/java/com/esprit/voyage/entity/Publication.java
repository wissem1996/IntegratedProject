package com.esprit.voyage.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_publication")
public class Publication implements Serializable {
	private static final long serialVersionUID = 6711457437559348053L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identifiant")
	int identifiant;

	@Column(name = "contenu")
	String contenu;

	@Column(name = "image_url")
	String pubImageUrl;

	@Column(name = "nombre_like")
	int nombreLike;

	@Column(name = "nombre_comments")
	int nombreComments;

	@Column(name = "reaction")
	String reaction;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_de_modification")
	Date dateDeModification;

	@ManyToOne
	@JoinColumn(name = "auteur_id", nullable = false)
	private Client auteur;

	@OneToMany(mappedBy = "publicationId")
	private Set<Commentaire> commentaire;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_de_creation")
	Date dateDeCreation;

	@ManyToOne
	@JoinColumn(name = "voyage_id", nullable = false)
	@JsonIgnore
	private Voyage voyageId;
	
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

	public String getPubImageUrl() {
		return pubImageUrl;
	}

	public void setPubImageUrl(String pubImageUrl) {
		this.pubImageUrl = pubImageUrl;
	}

	public int getNombreLike() {
		return nombreLike;
	}

	public void setNombreLike(int nombreLike) {
		this.nombreLike = nombreLike;
	}

	public int getNombreComments() {
		return nombreComments;
	}

	public void setNombreComments(int nombreComments) {
		this.nombreComments = nombreComments;
	}

	public String getReaction() {
		return reaction;
	}

	public void setReaction(String reaction) {
		this.reaction = reaction;
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

	public Set<Commentaire> getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(Set<Commentaire> commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateDeModification() {
		return dateDeModification;
	}

	public void setDateDeModification(Date dateDeModification) {
		this.dateDeModification = dateDeModification;
	}

	public Voyage getVoyageId() {
		return voyageId;
	}

	public void setVoyageId(Voyage voyageId) {
		this.voyageId = voyageId;
	}

}
