package fr.eni.projet_encheres.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.ArticleDAO;
import fr.eni.projet_encheres.dal.CategorieDAO;
import fr.eni.projet_encheres.dal.EnchereDAO;
import fr.eni.projet_encheres.dal.UtilisateurDAO;


@Service
public class EnchereServiceImpl implements EnchereService{
	
	private ArticleDAO articleDAO;
	private CategorieDAO categorieDAO;
	private UtilisateurDAO utilisateurDAO;
	private EnchereDAO enchereDAO;
	
	public EnchereServiceImpl(ArticleDAO articleDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO, EnchereDAO enchereDAO) {
		super();
		this.articleDAO = articleDAO;
		this.categorieDAO = categorieDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.enchereDAO = enchereDAO;
	}

	@Override
	public Categorie findCategorieById(int parseInt) {
		
		return this.categorieDAO.read(parseInt);
	}


	@Override
	public ArticleVendu findArticlesByCategorie(int categorieId) {
	
		return null;
	}

	@Override
	public List<Enchere> listeEncheresEnCours() {
		return articleDAO.selectEncheresEnCours();
	}

	@Override
	public Optional<Enchere> findEnchereMaxById(int id) {
				return  this.enchereDAO.selectEnchereMax(id);
	}

	@Override
	public void ajouterEnchere(Enchere enchere) {
		Optional<Utilisateur> utilisateur = utilisateurDAO.selectUtilisateurById(enchere.getIdUtilisateur());
		ArticleVendu articleEnCours = articleDAO.selectArticleById(enchere.getIdArticle());
		
		if ((articleEnCours.getPrixVente() < enchere.getMontantEnchere())&&(utilisateur != null)) {
			if (enchere.getMontantEnchere() <= utilisateur.get().getCredit()) {
				//Mise à jour du crédit de l'utilisateur
				//
				System.out.println(utilisateur.get().getCredit());
				long creditNouveau = utilisateur.get().getCredit() - enchere.getMontantEnchere();
				System.out.println("Le nouveau crédit est 1 : " + creditNouveau);
				utilisateur.get().setCredit(creditNouveau);
				System.out.println("Le nouveau crédit est 2 : " + utilisateur.get().getCredit());
				utilisateurDAO.updateUtilisateur(utilisateur.get());
				//Mise à jour éventuelle de l'utilisateur précédent
				//
				Optional<Enchere> enchereAncienne = enchereDAO.selectEnchereMax(enchere.getIdArticle());
				if (enchereAncienne != null) {
					Optional<Utilisateur> utilisateurAncien = utilisateurDAO.selectUtilisateurById(enchereAncienne.get().getIdUtilisateur());
					long creditAncien = utilisateurAncien.get().getCredit() + enchereAncienne.get().getMontantEnchere() ;
					utilisateurAncien.get().setCredit(creditAncien);
					System.out.println("Le nouveau crédit est 3 : " + utilisateurAncien.get().getCredit());
					utilisateurDAO.updateUtilisateur(utilisateurAncien.get());
				}
				
				enchereDAO.insertEnchere(enchere);
				articleEnCours.setPrixVente(enchere.getMontantEnchere());
				articleDAO.updateArticle(articleEnCours);
		
			}
			
		}
		
		
	}

	@Override
	public Enchere findEnchereById(int parseInt) {
		return enchereDAO.selectEnchereById(parseInt);
	}

	@Override
	public Optional<List<Enchere>> findEncheresEnCoursById(int id) {
		
		return enchereDAO.listeEncheresparId(id);
	}

//	@Override
//	public Optional<List<Enchere>> findEncheresByUtilisateur(int noUtilisateur) {
//		
//		return enchereDAO.listeEnchereByUtilisateur(noUtilisateur);
//	}

	
	
	
	
}
