package fr.eni.projet_encheres.dal;

import java.util.List;
import java.util.Optional;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.exception.BusinessException;

public interface UtilisateurDAO {

	/***
	 * Méthodes CRUD sur les utilisateurs
	 * 
	 * @param id
	 * @return
	 */
	Optional<Utilisateur> selectUtilisateurById(int id);

	void createUtilisateur(Utilisateur utilisateur);

	Utilisateur selectUtilisateurByUsername(String pseudo);
	
	void deleteUtilisateur(String pseudo) throws BusinessException;
	
	void updateUtilisateur(Utilisateur utilisateur);
	
	int countUtilisateurByEmail(String email);
	int countUtilisateurByPseudo(String pseudo);
	
	
	List<Enchere> selectEnchereByUsername(String pseudo);
	
	
	
}

