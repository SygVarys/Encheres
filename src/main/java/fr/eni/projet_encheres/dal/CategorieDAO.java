package fr.eni.projet_encheres.dal;

import java.util.List;

import fr.eni.projet_encheres.bo.Categorie;

public interface CategorieDAO {

	Categorie read(int id);
	
	List<Categorie> findAll();
}
