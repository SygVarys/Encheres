package fr.eni.projet_encheres.bo;

import java.io.Serializable;

public class Categorie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String libelle;
	
	/**
	 * 
	 * Constructeurs
	 */
	
	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Categorie(int id, String libelle) {
		this();
		this.id = id;
		this.libelle = libelle;
	}

	/***
	 * 
	 * Getters Setters
	 */

	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/****
	 * To String
	 */

	@Override
	public String toString() {
		return "Categorie [id=" + id + ", libelle=" + libelle + "]";
	}
	
	
	
	

}
