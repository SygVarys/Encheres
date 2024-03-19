package fr.eni.projet_encheres.bll;

import java.util.List;
import java.util.Optional;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;



public interface EnchereService {

	Categorie findCategorieById(int parseInt);
	
	ArticleVendu findArticlesByCategorie(int categorieId);
	
	List<Enchere> listeEncheresEnCours();
	
	Optional<Enchere> findEnchereMaxById(int id);
	
	void ajouterEnchere(Enchere enchere);

	Enchere findEnchereById(int parseInt);
	
	Optional<List<Enchere>> findEncheresEnCoursById(int id);
	
//	Optional<List<Enchere>> findEncheresByUtilisateur(int noUtilisateur);
}
