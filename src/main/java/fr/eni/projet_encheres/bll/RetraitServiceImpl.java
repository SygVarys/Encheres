package fr.eni.projet_encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.Retrait;
import fr.eni.projet_encheres.dal.ArticleDAO;
import fr.eni.projet_encheres.dal.CategorieDAO;
import fr.eni.projet_encheres.dal.RetraitDAO;
import fr.eni.projet_encheres.dal.UtilisateurDAO;


@Service
public class RetraitServiceImpl implements RetraitService {

	private RetraitDAO retraitDAO;
	private ArticleDAO articleDAO;
	private CategorieDAO categorieDAO;
	private UtilisateurDAO utilisateurDAO;
	
	
	
	public RetraitServiceImpl(RetraitDAO retraitDAO, ArticleDAO articleDAO, CategorieDAO categorieDAO,
			UtilisateurDAO utilisateurDAO) {
		super();
		this.retraitDAO = retraitDAO;
		this.articleDAO = articleDAO;
		this.categorieDAO = categorieDAO;
		this.utilisateurDAO = utilisateurDAO;
	}



	@Override
	public void addRetrait(Retrait retrait) {
		retraitDAO.createRetrait(retrait);
		
	}



	@Override
	public Retrait findRetraitByArticle(int articleId) {
		
		return retraitDAO.findRetraitByArticle(articleId) ;
	}
	
	
	
	
	
}
