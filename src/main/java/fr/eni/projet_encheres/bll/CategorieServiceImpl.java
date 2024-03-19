package fr.eni.projet_encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.dal.ArticleDAO;
import fr.eni.projet_encheres.dal.CategorieDAO;
import fr.eni.projet_encheres.dal.UtilisateurDAO;

@Service
public class CategorieServiceImpl implements CategorieService{

	private ArticleDAO articleDAO;
	private CategorieDAO categorieDAO;
	private UtilisateurDAO utilisateurDAO;
	
	public CategorieServiceImpl(ArticleDAO articleDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO) {
		super();
		this.articleDAO = articleDAO;
		this.categorieDAO = categorieDAO;
		this.utilisateurDAO = utilisateurDAO;
	}
	
	
	@Override
	public List<Categorie> getCategories(){
		System.out.println("appel de chargerCategoriesEnSession 2");
		
			List<Categorie> listeCategories = this.categorieDAO.findAll();
			return listeCategories;
		
	}
	
	@Override
	public Categorie findCategorieById(int parseInt) {
		System.out.println("toto est là 1");
		return this.categorieDAO.read(parseInt);
	}


	@Override
	public List<ArticleVendu> findArticlesByCategorie(Integer categorieId) {
		return articleDAO.selectArticlesByCategorie(categorieId);
	}


//	private List<ArticleVendu> selectArticleByCategorie(Integer categorieId) {
//		return null;
//	}


	@Override
	public List<ArticleVendu> findAllArticles() {
		return articleDAO.findAll();
	}
	
}
