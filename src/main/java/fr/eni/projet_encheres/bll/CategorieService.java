package fr.eni.projet_encheres.bll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;


@Service
public interface CategorieService {

	
	public List<Categorie> getCategories();
	
	Categorie findCategorieById(int parseInt);

	public List<ArticleVendu> findArticlesByCategorie(Integer categorieId);

	public List<ArticleVendu> findAllArticles();
	
}
