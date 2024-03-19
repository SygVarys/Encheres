package fr.eni.projet_encheres.bo;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Enchere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int idUtilisateur;
	private int idArticle;
	private Date dateEnchere;
	private int montantEnchere;
	
	
	/***
	 * Méthode ToString
	 */
	
	
	@Override
	public String toString() {
		return "Enchere [idUtilisateur=" + idUtilisateur + ", idArticle=" + idArticle + ", dateEnchere=" + dateEnchere
				+ ", montantEnchere=" + montantEnchere + "]";
	}



	public Enchere() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * 
	 * Constructeur
	 */

	public Enchere(int idUtilisateur, int idArticle, Date dateEnchere, int montantEnchere) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.idArticle = idArticle;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}
	
	

	/***
	 * 
	 * Getter Setter
	 */
	
	
	public int getIdUtilisateur() {
		return idUtilisateur;
	}



	



	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}



	public int getIdArticle() {
		return idArticle;
	}



	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}



	public Date getDateEnchere() {
		return dateEnchere;
	}



	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}



	public int getMontantEnchere() {
		return montantEnchere;
	}



	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	
	
	
	

}
