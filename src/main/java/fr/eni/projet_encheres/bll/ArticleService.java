package fr.eni.projet_encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.exception.BusinessException;
import jakarta.validation.Valid;


public interface ArticleService {

	List<ArticleVendu> getArticles();

	List<Categorie> findAllCategories();

	void add(ArticleVendu articleVendu) throws BusinessException;
	
	ArticleVendu findArticleById(int id);
	
	List<ArticleVendu> findArticlesByNoUtilisateur(int id);

	List<ArticleVendu> findAllArticlesByNoUtilisateurEnCours(int id);

	boolean enchereTerminee(int id);
}
