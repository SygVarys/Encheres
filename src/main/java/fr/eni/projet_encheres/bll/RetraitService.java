package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.Retrait;

public interface RetraitService{

	
	void addRetrait(Retrait retrait);

	Retrait findRetraitByArticle(int articleId);
}
