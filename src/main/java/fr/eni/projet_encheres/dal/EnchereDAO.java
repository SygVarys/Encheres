package fr.eni.projet_encheres.dal;

import java.util.List;
import java.util.Optional;

import fr.eni.projet_encheres.bo.Enchere;

public interface EnchereDAO {
	
	Optional<Enchere> selectEnchereMax(int id);

	Boolean compareToMax(int idArticle, int proposition);
	
	void insertEnchere(Enchere enchere);

	Enchere selectEnchereById(int parseInt);
	
	boolean enchereTerminee(int id);

	Optional<List<Enchere>> listeEncheresparId(int id);
	
	//Optional<List<Enchere>> listeEnchereByUtilisateur(int noUtilisateur);

}
