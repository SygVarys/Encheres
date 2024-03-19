package fr.eni.projet_encheres.ihm;

import java.security.Principal;

import org.springframework.boot.system.SystemProperties;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bll.UtilisateurService;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {

	UtilisateurService utilisateurService;
	
	
	//Autowired
	public UtilisateurController(UtilisateurService utilisateurService) {
		super();
		this.utilisateurService = utilisateurService;
	}
	
	@GetMapping("/inscription")
	public String afficherInscription(Model modele) {
		Utilisateur utilisateur = new Utilisateur();
		modele.addAttribute("utilisateur", utilisateur);
		return "inscription";
	}
	
	@PostMapping("/inscription")
	public String creerUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
			BindingResult bindingResult, Model model, @RequestParam("confirmerMotDePasse") String mdpConfirmation) throws BusinessException {
		
		System.out.println("MotDePasse de Confirmation : " + mdpConfirmation);
		System.out.println("utilisateur = " + utilisateur.getMotDePasse());
		System.out.println("utilisateur = " + utilisateur);
		//utilisateurService.add(utilisateur);
		
		//return "encheres";
		
		if(bindingResult.hasErrors()) {
			return "inscription";
		}else {
		
		try {
			utilisateurService.add(utilisateur, mdpConfirmation);
			return "redirect:/encheres";
		} catch (BusinessException e) {
			e.getMessages().forEach(m->{
				ObjectError error = new ObjectError("globalError", m);
				bindingResult.addError(error);
	
			});
			
			return "inscription";
		}
		
	}
	}
	
	@GetMapping("/annulerInscription")
	public String annulerInscription() {
		return "redirect:/encheres";
	}
	
	
	@GetMapping("/mdp-oublie")
	public String affichermdpoublie() {
		
		
		return "mot-de-passe-oublie";
	}
	
	
	
	
	@GetMapping("/modifProfil")
	public String modifierProfil(Model modele, Principal principal) {
		
		Utilisateur utilisateur = utilisateurService.findUtilisateurByUsername(principal.getName());
    	System.out.println("Controller nouveau : "  + principal.getName() + "    " + utilisateur.getId() + " comme id");
    	modele.addAttribute("utilisateur", utilisateur);
		return "modifProfil";
	}
	
	
	@PostMapping("/modifProfil")
	public String modifierProfilSuite(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model, @RequestParam("motDePasseActuel") String motDePasseActuel, @RequestParam("confirmerMotDePasse") String confirmerMotDePasse, Principal principal) throws BusinessException {
		System.err.println("Nouvel utilisateur" + utilisateur.toString());
		System.err.println("Mot de passe Actuel " + motDePasseActuel );
		System.err.println("mot de passe réel en base "  + utilisateurService.findUtilisateurByUsername(principal.getName()).getMotDePasse() );
		System.out.println(BCrypt.checkpw(motDePasseActuel, utilisateurService.findUtilisateurByUsername(principal.getName()).getMotDePasse()));
		System.out.println("Mot de passe modifié : " + confirmerMotDePasse);
		System.out.println("Nouveau Mot de passe rentré en premier : " + utilisateur.getMotDePasse());
		
		if(bindingResult.hasErrors()) {
			System.out.println("Je passe par la 1");
			System.out.println(bindingResult.getFieldErrors().toString());
			return "modifProfil";
		}else {
		
		try {
			utilisateurService.updateUtilisateur(utilisateur, motDePasseActuel, confirmerMotDePasse, principal);
			return "redirect:/profil";
		} catch (BusinessException e) {
			e.getMessages().forEach(m->{
				ObjectError error = new ObjectError("globalError", m);
				bindingResult.addError(error);
	
			});
			System.out.println("Je passe par la 2");
			return "modifProfil";
		}
		
		}
		
	}
	
	@GetMapping("/supprimer")
	public String supprimerProfil(Model modele, Principal principal) {
		
		Utilisateur utilisateur = utilisateurService.findUtilisateurByUsername(principal.getName());
    	System.out.println("Controller nouveau : Suppression de la personne :  "  + principal.getName() + "    " + utilisateur.getId() + " comme id");
    	modele.addAttribute("utilisateur", utilisateur);
    	try {
			utilisateurService.deleteUtilisateur(principal.getName());
			return "redirect:logout";
		} catch (BusinessException e) {
		
			e.printStackTrace();
		}
		return "redirect:encheres";
	}
	
	
}
