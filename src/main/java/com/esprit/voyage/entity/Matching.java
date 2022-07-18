package com.esprit.voyage.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MATCHING")
public class Matching {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
//    int id;

    @Id
    @Column(name = "CLIENT_ID")
    @NotNull
    long idClient;

    @OneToMany()
    @JoinTable(name = "T_CLT_CLIENTLIST", joinColumns = {@JoinColumn(name = "CLIENT_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "id")})
    @NotNull
    List<Client> clientList = new ArrayList<>();

    @Column(name = "ACTIVITY_FIELD")
    @NotNull
    boolean isSameActivityField;

    public Matching() {
        super();
    }

    public Matching(long idClient, boolean isSameActivityField, List<Client> clientList) {
        super();
        this.idClient = idClient;
        this.clientList = clientList;
        this.isSameActivityField = isSameActivityField;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public boolean getIsSameActivityField() {
        return isSameActivityField;
    }

    public void setIsSameActivityField(boolean isSameActivityField) {
        this.isSameActivityField = isSameActivityField;
    }
}
