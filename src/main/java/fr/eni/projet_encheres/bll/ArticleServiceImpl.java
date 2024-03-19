package fr.eni.projet_encheres.bll;


import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.dal.ArticleDAO;
import fr.eni.projet_encheres.dal.CategorieDAO;
import fr.eni.projet_encheres.dal.RetraitDAO;
import fr.eni.projet_encheres.dal.UtilisateurDAO;
import fr.eni.projet_encheres.exception.BusinessException;

@Service
public class ArticleServiceImpl implements ArticleService {

	private ArticleDAO articleDAO;
	private CategorieDAO categorieDAO;
	private UtilisateurDAO utilisateurDAO;
	private RetraitDAO retraitDAO;
	
	public ArticleServiceImpl(ArticleDAO articleDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO, RetraitDAO retraitDAO) {
		super();
		this.articleDAO = articleDAO;
		this.categorieDAO = categorieDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.retraitDAO = retraitDAO;
	}
	
	
	
	
	@Override
	public List<ArticleVendu> getArticles() {

		List<ArticleVendu> listeArticles = this.articleDAO.findAll();
		listeArticles.forEach(
				a->a.setCategorieArticle(this.categorieDAO.read(a.getCategorieArticle().getId())));	
				
		listeArticles.forEach(
				a->a.setVendeur(this.utilisateurDAO.selectUtilisateurById(a.getVendeur().getId()).get()));
				
		return listeArticles;
	}




	@Override
	public List<Categorie> findAllCategories() {
		return categorieDAO.findAll();
	}




	@Override
	public void add(ArticleVendu articleVendu) throws BusinessException {
		BusinessException e = new BusinessException();

		if (articleVendu.getDateFinEncheres().before(articleVendu.getDateDebutEncheres())) {	
		e.add("La date de début d'enchere est postérieure ou égale à la date de fin d'enchere.");
		}
		
		if (articleVendu.getDateDebutEncheres().before(new Date(System.currentTimeMillis()))) {
			e.add("La date de début d'enchere est antérieure ou égale à la date du jour.");
		}
		
		if (e.getMessages().isEmpty()) {
		//System.err.println("Messages d'erreur " + e.getMessage());
		
		//System.err.println("Messages d'erreur " + articleVendu.toString());
		articleDAO.createArticle(articleVendu);
		//System.err.println("Messages d'erreur " + articleVendu.toString());
		retraitDAO.createRetrait(articleVendu.getLieuRetrait());
		} else {
			throw e;
		}
		

	}
	
	@Override
	public boolean enchereTerminee(int id) {
		return articleDAO.enchereTerminee(id);
	}



	@Override
	public ArticleVendu findArticleById(int id) {
		return articleDAO.selectArticleById(id);
	}

	
	public List<ArticleVendu> findArticlesByNoUtilisateur(int id) {
		return articleDAO.selectArticlesByNoUtilisateur(id);
	}
	
	public List<ArticleVendu> findAllArticlesByNoUtilisateurEnCours(int id){
		return articleDAO.selectArticlesByNoUtilisateurEnCours(id);
	}
	
}
