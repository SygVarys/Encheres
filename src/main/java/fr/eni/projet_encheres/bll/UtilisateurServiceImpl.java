package fr.eni.projet_encheres.bll;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet_encheres.exception.BusinessException;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.ArticleDAO;
import fr.eni.projet_encheres.dal.CategorieDAO;
import fr.eni.projet_encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private ArticleDAO articleDAO;
	private CategorieDAO categorieDAO;
	private UtilisateurDAO utilisateurDAO;

	public UtilisateurServiceImpl(ArticleDAO articleDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO) {
		super();
		this.articleDAO = articleDAO;
		this.categorieDAO = categorieDAO;
		this.utilisateurDAO = utilisateurDAO;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<Utilisateur> findUtilisateur(int int1) {
		return utilisateurDAO.selectUtilisateurById(int1);
	}

	/***
	 * La méthode add prend en paramètre un utilisateur, le mdp de confirmation :
	 * 
	 * nbMail calcule le nombre de mails identiques déjà dans la base nbPseudo
	 * calcule le nombre de mails identiques déjà dans la base mdpConfirmation
	 * compare au mdp à enregistrer et renvoie une erreur si pas de correspondance
	 * Si tout va bien enregistrement en base.
	 * 
	 */
	@Override
	@Transactional
	public void add(Utilisateur utilisateur, String mdpConfirmation) throws BusinessException {
		BusinessException e = new BusinessException();

		int nbEmail = utilisateurDAO.countUtilisateurByEmail(utilisateur.getEmail());
		int nbPseudo = utilisateurDAO.countUtilisateurByPseudo(utilisateur.getPseudo());

		// Test de l'existence en base des identifiants avant enregistrement : email et
		// pseudo
		// Test de la correspondance mdp et mdpConfirmation
		if (!utilisateur.getMotDePasse().equals(mdpConfirmation)) {
			e.add("Mot de passe et confirmation de mot de passe ne correspondent pas");
		}

		if (nbEmail > 0) {
			e.add("Email déjà enregistré. Vous ne pouvez pas créé un utilisateur existant.");
		}

		if (nbPseudo > 0) {
			e.add("Pseudo déjà enregistré. Vous ne pouvez pas créé un utilisateur existant.");
		}

		// Si pas d'erreur, enregistrement en base du nouvel utilisateur
		if (e.getMessages().size() == 0) {
			utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
			utilisateurDAO.createUtilisateur(utilisateur);
		} else {
			throw e;
		}

	}

	/***
	 * Permet un appel en base de données pour retrouver un utilisateur à partir de
	 * son identifiant
	 * 
	 * 
	 */
	@Override
	public Utilisateur findUtilisateurByUsername(String pseudo) {
		return utilisateurDAO.selectUtilisateurByUsername(pseudo);
	}

	/***
	 * 
	 * Permet la suppression d'un utilisateur en base de données
	 * 
	 */
	@Override
	@Transactional
	public void deleteUtilisateur(String pseudo) throws BusinessException {
		this.utilisateurDAO.deleteUtilisateur(pseudo);

	}

	/***
	 * 
	 * updateUtilisateur met à jour l'utilisateur connecté utilisateur contient
	 * l'utilisateur avec les données modifiées dans le formulaire motDePasseActuel
	 * contient le mot de passe de connexion à comparer avec le mot de passe actuel
	 * pour vérifier que c'est bien l'utilsiateur confirmerMotDePasse correspond à
	 * la frappe de la seconde fois principal permet de récupérer l'utilisateur
	 * connecté
	 * 
	 * A l'issu des tests sur les mots de passe, en cas de succès, l'utilisateur est
	 * enregistré sinon Bussiness Exception est renvoyée
	 * 
	 */
	@Override
	public void updateUtilisateur(Utilisateur utilisateur, String motDePasseActuel, String confirmerMotDePasse,
			Principal principal) throws BusinessException {
		BusinessException e = new BusinessException();
		// String ancienPassword = this.findUtilisateurByUsername(principal.getName()).getMotDePasse();

		if (!BCrypt.checkpw(motDePasseActuel, this.findUtilisateurByUsername(principal.getName()).getMotDePasse())) {
			e.add("Le mot de passe rentré ne correspond pas au mot de passe actuel");
		}

		if (!confirmerMotDePasse.equals(utilisateur.getMotDePasse())) {
			e.add("Le nouveau mot de passe et sa confirmation ne correspondent pas.");
		}

		// Si pas d'erreur, enregistrement en base du nouvel utilisateur
		// System.out.println(e.getMessage().isEmpty());
		if (e.getMessages().size() == 0) {

			String passwordEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
			utilisateur.setMotDePasse(passwordEncode);
			utilisateurDAO.updateUtilisateur(utilisateur);
		} else {
			throw e;
		}

	}

}
