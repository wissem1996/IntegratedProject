package com.esprit.voyage.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "model_reclamation")
public class Reclamation {

	private static final long serialVersionUID = 6711457437559348053L; 
	@Id
	@GeneratedValue
	private int id;
	@NotNull(message = "Decription can not be null")
	@Size(min = 3, max = 100, message = "Decription size must be between 3 and 1500 characters" )
	private String description;
	@NotNull(message = "Date of reclamation can not be null")
	private Date dateReclamation;
	@NotNull(message = "Status of reclamation can not be null")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String action;
	private String sujet;
	private Date dateTraitement;
	private String nomClientReclame;
	
	//private Client assignee;
	@ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;
	
	@ManyToOne
    @JoinColumn(name="assignee_id", nullable=false)
    private Client assignee;
	
	@ManyToOne
    @JoinColumn(name="lastAssignee_id", nullable=false)
    private Client lastAssignee;
	
	public Reclamation() {
		
	}
	
	public Reclamation(
			@NotNull(message = "Decription can not be null") @Size(min = 3, max = 100, message = "Decription size must be between 3 and 1500 characters") String description,
			@NotNull(message = "Date of reclamation can not be null") Date dateReclamation) {
		super();
		this.description = description;
		this.dateReclamation = dateReclamation;
		this.status = Status.OPEN;
	}


	public Reclamation(
			@NotNull(message = "Decription can not be null") @Size(min = 3, max = 100, message = "Decription size must be between 3 and 1500 characters") String description,
			@NotNull(message = "Date of reclamation can not be null") Date dateReclamation,
			@NotNull(message = "Status of reclamation can not be null") Status status) {
		super();
		this.description = description;
		this.dateReclamation = dateReclamation;
		this.status = status;
	}

	

	public Reclamation(
			@NotNull(message = "Decription can not be null") @Size(min = 3, max = 100, message = "Decription size must be between 3 and 1500 characters") String description,
			@NotNull(message = "Date of reclamation can not be null") Date dateReclamation,
			@NotNull(message = "Status of reclamation can not be null") Status status, String action, String sujet,
			Date dateTraitement, String nomClientReclame, Client client) {
		super();
		this.description = description;
		this.dateReclamation = dateReclamation;
		this.status = status;
		this.action = action;
		this.sujet = sujet;
		this.dateTraitement = dateTraitement;
		this.nomClientReclame = nomClientReclame;
		this.client = client;
	}

	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


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
	public Date getDateReclamation() {
		return dateReclamation;
	}
	public void setDateReclamation(Date dateReclamation) {
		this.dateReclamation = dateReclamation;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Date getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	public String getNomClientReclame() {
		return nomClientReclame;
	}

	public void setNomClientReclame(String nomClientReclame) {
		this.nomClientReclame = nomClientReclame;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getAssignee() {
		return assignee;
	}

	public void setAssignee(Client assignee) {
		this.assignee = assignee;
	}

	public Client getLastAssignee() {
		return lastAssignee;
	}

	public void setLastAssigne(Client lastAssignee) {
		this.lastAssignee = lastAssignee;
	}
	
	
	
	
}
