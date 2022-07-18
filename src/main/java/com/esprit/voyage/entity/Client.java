package com.esprit.voyage.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twilio.type.PhoneNumber;

@Entity
@Table(name = "model_client", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")})
public class Client {
    private static final long serialVersionUID = 6711457437559348053L;
    @Id
    @GeneratedValue
    private int id;

    @Size(max = 30)
    private String name;

    @Size(max = 30)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 50)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "participation", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events = new HashSet<>();

    private String numTel;
    private long voyageID;
    
    @Enumerated(EnumType.STRING)
	private Profession profession;

    private Date CreationDate;
    
    public long getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(long voyageID) {
        this.voyageID = voyageID;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    private int nbReclamation;

    private Boolean isDisabled;
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Reclamation> reclamations;

    @OneToMany(mappedBy = "auteur")
    @JsonIgnore
    private Set<Publication> publications;

    @OneToMany(mappedBy = "auteur")
    @JsonIgnore
    private Set<Commentaire> commentaire;

    public Client(String name, String username, String email) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
    }

	public Client(String name, String email, String password, String username, String numTel, Profession profession) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.numTel = numTel;
		this.profession = profession;
		this.CreationDate = new Date();
		this.isDisabled = false;
	}

    public Client(String name, String username, String email, int nbReclamation, Set<Reclamation> reclamations) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.nbReclamation = nbReclamation;
        this.reclamations = reclamations;
    }

    public Client() {
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNbReclamation() {
        return nbReclamation;
    }

    public void setNbReclamation(int nbReclamation) {
        this.nbReclamation = nbReclamation;

    }

    public Set<Reclamation> getReclamations() {
        return reclamations;
    }

    public void setReclamations(Set<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public PhoneNumber getNumTel() {
        return new PhoneNumber(this.numTel);
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    public Set<Commentaire> getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Set<Commentaire> commentaire) {
        this.commentaire = commentaire;
    }

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public Date getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(Date creationDate) {
		CreationDate = creationDate;
	}

}
