package fr.eni.projet_encheres.dal;

import java.util.List;

import fr.eni.projet_encheres.bo.Retrait;

public interface RetraitDAO {

	
	List<Retrait> findAllRetrait();

	void createRetrait(Retrait retrait);

	Retrait findRetraitByArticle(int articleId);
	
}
