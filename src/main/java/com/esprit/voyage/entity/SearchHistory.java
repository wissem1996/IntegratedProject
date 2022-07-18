package com.esprit.voyage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SEARCH_HISTORY")
public class SearchHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id;

    @Column(name = "CLIENT_ID")
    @NotNull
    int idClient;

    @Column(name = "ADRESSE")
    @NotNull
    @Size(min = 5, max = 30)
    String adresse;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "START_DATE")
    @NotNull
    Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "END_DATE")
    @NotNull
    Date endDate;

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
