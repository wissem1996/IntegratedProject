package com.esprit.voyage.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author mokgharbi
 */
@Entity
public class Event implements Serializable {


    @Id
    @GeneratedValue
    @Column(name ="EVENT_ID")
    private int id;
    private  String libellé , description , adresse;
    private Date dateDebutEvent , dateFinEvent ;
    private int nbrParticipants ;


    @Column(nullable = false)
    private Integer nbrDePlaces;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "participation",
            joinColumns={@JoinColumn(name="event_id")},
            inverseJoinColumns={@JoinColumn(name ="user_id")})
    private Set<Client> users = new HashSet<>();

    public Integer getNbrDePlaces() {
        return nbrDePlaces;
    }

    public void setNbrDePlaces(Integer nbrDePlaces) {
        this.nbrDePlaces = nbrDePlaces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibellé() {
        return libellé;
    }

    public void setLibellé(String libellé) {
        this.libellé = libellé;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateDebutEvent() {
        return dateDebutEvent;
    }

    public void setDateDebutEvent(Date dateDebutEvent) {
        this.dateDebutEvent = dateDebutEvent;
    }

    public Date getDateFinEvent() {
        return dateFinEvent;
    }

    public void setDateFinEvent(Date dateFinEvent) {
        this.dateFinEvent = dateFinEvent;
    }

    public int getNbrParticipants() {
        return nbrParticipants;
    }

    public void setNbrParticipants(int nbrParticipants) {
        this.nbrParticipants = nbrParticipants;
    }

    public Set<Client> getUsers() {
        return users;
    }

    public void setUsers(Set<Client> users) {
        this.users = users;
    }

    public Event() {
    }

    public Event(int id, String libellé, String description, String adresse, Date dateDebutEvent, Date dateFinEvent, int nbrParticipants, @NotNull(message = "salary may not be empty") Integer nbrDePlaces, Set<Client> users) {
        this.id = id;
        this.libellé = libellé;
        this.description = description;
        this.adresse = adresse;
        this.dateDebutEvent = dateDebutEvent;
        this.dateFinEvent = dateFinEvent;
        this.nbrParticipants = nbrParticipants;
        this.nbrDePlaces = nbrDePlaces;
        this.users = users;
    }
}
