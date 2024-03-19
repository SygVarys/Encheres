package fr.eni.projet_encheres.bll;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.exception.BusinessException;

@Service
public interface UtilisateurService {
	
	Optional<Utilisateur> findUtilisateur(int int1);
	
	Utilisateur findUtilisateurByUsername(String pseudo);

	void add(Utilisateur utilisateur, String mdpConfirmation) throws BusinessException;
	
	void deleteUtilisateur(String pseudo) throws BusinessException;
	
	//void updateUtilisateur(Utilisateur utilisateur) throws BusinessException;

	//void updateUtilisateur(Utilisateur utilisateur, String motDePasseActuel, String confirmerMotDePasse) throws BusinessException;

	void updateUtilisateur(Utilisateur utilisateur, String motDePasseActuel, String confirmerMotDePasse,
			Principal principal) throws BusinessException;

}
