package com.esprit.voyage.entity;

public class Statistique {

	String month,nbuser;

	
	
	public Statistique(String month, String nbuser) {
		super();
		this.month = month;
		this.nbuser = nbuser;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getNbuser() {
		return nbuser;
	}

	public void setNbuser(String nbuser) {
		this.nbuser = nbuser;
	}
	
}
