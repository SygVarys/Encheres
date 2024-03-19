package fr.eni.projet_encheres.dal;

import java.util.List;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Enchere;

public interface ArticleDAO {

	
	List<ArticleVendu> findAll();

	void createArticle(ArticleVendu articleVendu);

	List<Enchere> selectEncheresEnCours();

	ArticleVendu selectArticleById(int id);

	List<ArticleVendu> selectArticlesByCategorie(Integer categorieId);
	
	void updateArticle(ArticleVendu articleVendu);
	
	List<ArticleVendu> selectArticlesByNoUtilisateur(Integer noUtilisateur);
	
	List<ArticleVendu> selectArticlesByNoUtilisateurEnCours(Integer noUtilisateur);

	boolean enchereTerminee(int id);
	
}
