package fr.eni.projet_encheres.bo;

import java.io.Serializable;
import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ArticleVendu implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noArticle;
	
	@NotBlank (message="Le nom est obligatoire")
	@Size(min=1, max=30, message="Le nom doit contenir moins de 30 caractères")
	private String nomArticle;
	
	@NotBlank (message="La description est obligatoire")
	@Size(min=1, max=30, message="Le description doit contenir moins de 300 caractères")
	private String description;
	
	@NotNull(message="Une date doit être mise pour le début des enchères")
	private Date dateDebutEncheres;
	
	@NotNull(message="Une date doit être mise pour la fin des enchères")
	private Date dateFinEncheres;
	
	@Min(value = 0, message = "La valeur minimale doit être de 0")
	private int miseAPrix;
	
	private int prixVente;
	
	private int etatVente;
	
	private Retrait lieuRetrait;
	
	private Utilisateur vendeur;
	
	//private Utilisateur acheteur;
	
	private Categorie categorieArticle;
	
	private Enchere enchere;
	
	
	
	
	
	public ArticleVendu() {
		super();
	}


	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int miseAPrix, int prixVente, int etatVente) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
	}

	
	
	
	
	

	


	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int miseAPrix, int prixVente, Utilisateur vendeur, Categorie categorieArticle) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.vendeur = vendeur;
		this.categorieArticle = categorieArticle;
	}


	public int getNoArticle() {
		return noArticle;
	}


	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}


	public String getNomArticle() {
		return nomArticle;
	}


	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}


	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}


	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}


	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}


	public int getMiseAPrix() {
		return miseAPrix;
	}


	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}


	public int getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public int getEtatVente() {
		return etatVente;
	}


	public void setEtatVente(int etatVente) {
		this.etatVente = etatVente;
	}


//	public Retrait getRetrait() {
//		return lieuRetrait;
//	}
//
//
//	public void setRetrait(Retrait retrait) {
//		this.lieuRetrait = retrait;
//	}
	

	public Utilisateur getVendeur() {
		return vendeur;
	}


	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}


//	public Utilisateur getAcheteur() {
//		return acheteur;
//	}
//
//
//	public void setAcheteur(Utilisateur acheteur) {
//		this.acheteur = acheteur;
//	}

	
	
	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}


	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}


	public Categorie getCategorieArticle() {
		return categorieArticle;
	}


	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}


	public Enchere getEnchere() {
		return enchere;
	}


	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}


	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres.toString() + ", dateFinEncheres=" + dateFinEncheres.toString() + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", lieuRetrait=" + lieuRetrait
				+ ", vendeur=" + vendeur + ", categorieArticle=" + categorieArticle + ", enchere=" + enchere + "]";
	}


	


	
	
	
	
	
	
	
}
