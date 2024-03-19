package fr.eni.projet_encheres.ihm;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bll.UtilisateurService;
import fr.eni.projet_encheres.bo.Utilisateur;

@Controller
@SessionAttributes({"utilisateur"})
public class GetUserWithPrincipalController {
	
	UtilisateurService utilisateurService;
	EnchereService enchereService;
	

	 	public GetUserWithPrincipalController(UtilisateurService utilisateurService, EnchereService enchereService) {
		super();
		this.utilisateurService = utilisateurService;
		this.enchereService = enchereService;
	}



		//@GetMapping("/profil")
	    @ResponseBody
	    public String currentUserName(Principal principal, Model model) {
	 		
	        return principal.getName() ;
	    }
		
		@GetMapping("/profil")
		public String login(Principal principal, Model model) {
			Utilisateur utilisateur;
	 		utilisateur = utilisateurService.findUtilisateurByUsername(principal.getName());
	    	System.out.println("Controller nouveau : "  + principal.getName() + "     " + utilisateur.getId());
	    	System.err.println("Test de la méthode enchere service pour trouver les enchères " + enchereService.findEncheresEnCoursById(2));
	    	System.err.println("Je teste mon utilisation de la requete affichant les encheres en cours en tant que vendeur" + enchereService.findEncheresEnCoursById(utilisateur.getId()).get().toString() );
	    	model.addAttribute("utilisateur", utilisateur);
	    	System.out.println(utilisateur.toString());
			System.out.println(model.getAttribute("utilisateur").toString());
			return "profil";
		}

}
