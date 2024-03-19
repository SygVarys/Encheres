package fr.eni.projet_encheres.bo;

import java.io.Serializable;

public class Retrait implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rue;
	private String codePostal;
	private String ville;
	private ArticleVendu article;
	
	
	
	public Retrait() {
		super();
	}

	

	public Retrait(String rue, String codePostal, String ville) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	


	public Retrait(ArticleVendu article,String rue, String codePostal, String ville) {
		super();
		this.article = article;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		
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



	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}



	public String getVille() {
		return ville;
	}



	public void setVille(String ville) {
		this.ville = ville;
	}



	public ArticleVendu getArticle() {
		return article;
	}



	public void setArticle(ArticleVendu article) {
		this.article = article;
	}



	@Override
	public String toString() {
		return "Retrait [rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + ", article=" + article
				+ "]";
	}
	
	
	
	
	
}
