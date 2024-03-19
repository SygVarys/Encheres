package fr.eni.projet_encheres.bo;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur implements Serializable{

	/**
	 * Attributs avec les controles de validation
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	@NotBlank (message="Le pseudo est obligatoire")
	@Size(min=1, max=30, message="Le pseudo doit contenir moins de 30 caractères")
	private String pseudo;
	
	@NotBlank (message="Le nom est obligatoire")
	@Size(min=1, max=30, message="Le nom doit contenir moins de 30 caractères")
	private String nom;
	
	@NotBlank (message="Le prénom est obligatoire")
	@Size(min=1, max=30, message="Le prénom doit contenir moins de 30 caractères")
	private String prenom;
	
	@NotBlank (message="L'email est obligatoire")
	@Email(message="Merci d'indiquer un email valide")
	private String email;
	
	@NotBlank (message="Le numéro de téléphone est obligatoire")
	@Pattern(regexp = "(\\+33|0)[0-9]{9}")
	private String telephone;
	
	@NotBlank (message="Le nom de la rue est obligatoire")
	@Size(min=1, max=30, message="Le nom de la rue doit contenir moins de 30 caractères")
	private String rue;
	
	@NotBlank (message="Le code postal est obligatoire")
	@Pattern(regexp = "[0-9]{5}")
	private String codePostal;
	
	@NotBlank (message="Le nom de la ville est obligatoire")
	@Size(min=1, max=30, message="Le nom de la ville doit contenir moins de 30 caractères")
	private String ville;
	
	private long credit;
	private boolean administrateur;
	
	/***
	 * Avec beaucoup de précaution, je récupère le mot de passe
	 */
	
	private String motDePasse;
	
	
	/***
	 * 
	 * Constructeurs de la classe
	 * 
	 */
	
	
	
	
	public Utilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}



	
	

	public Utilisateur(int id, String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, long credit ) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
		this.motDePasse = motDePasse;
	}


	



	
	public Utilisateur(int id, String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal,String ville, String motDePasse, long credit, boolean administrateur) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
		this.administrateur = administrateur;
		this.motDePasse = motDePasse;
	}












	/**
	 * 
	 * Getters setters
	 */




	public int getId() {
		return id;
	}







	public void setId(int id) {
		this.id = id;
	}







	public String getPseudo() {
		return pseudo;
	}







	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}







	public String getNom() {
		return nom;
	}







	public void setNom(String nom) {
		this.nom = nom;
	}







	public String getPrenom() {
		return prenom;
	}







	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}







	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public String getTelephone() {
		return telephone;
	}







	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}







	public String getRue() {
		return rue;
	}







	public void setRue(String rue) {
		this.rue = rue;
	}







	public String getCodePostal() {
		return codePostal;
	}







	public void setVille(String ville) {
		this.ville = ville;
	}

	
	public String getVille() {
		return ville;
	}







	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	






	public long getCredit() {
		return credit;
	}







	public void setCredit(long credit) {
		this.credit = credit;
	}







	public boolean getAdministrateur() {
		return administrateur;
	}







	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}





	public String getMotDePasse() {
		return motDePasse;
	}












	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}












	/**
	 * ToString
	 * 
	 */







	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom=" + prenom + ", email="
				+ email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal=" + codePostal + ", ville="
						+ ville +", credit=" + credit + ", administrateur=" + administrateur + ", motDePasse=" + motDePasse + "]";
	}


	
	




	
	
	
	
	
	
	
	
	
	
	

}
