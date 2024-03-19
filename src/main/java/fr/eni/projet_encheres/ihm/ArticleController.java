package fr.eni.projet_encheres.ihm;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.projet_encheres.bll.ArticleService;
import fr.eni.projet_encheres.bll.ArticleServiceImpl;
import fr.eni.projet_encheres.bll.CategorieService;
import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bll.RetraitService;
import fr.eni.projet_encheres.bll.UtilisateurService;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Retrait;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.exception.BusinessException;
import jakarta.validation.Valid;


@Controller
public class ArticleController {

	ArticleService articleService;
	UtilisateurService utilisateurService;
	EnchereService enchereService;
	RetraitService retraitService;
	

	public ArticleController(ArticleService articleService, UtilisateurService utilisateurService, EnchereService enchereService, RetraitService retraitService) {
		super();
		this.articleService = articleService;
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
	}
	
	@GetMapping("/nouvelleVente")
	public String afficherInscription(Model modele, Principal principal) {
		ArticleVendu articleVendu = new ArticleVendu();
		Retrait retrait = new Retrait();
		
		Utilisateur utilisateur = utilisateurService.findUtilisateurByUsername(principal.getName());  // récupère l'utilisateur connecté
		retrait.setRue(utilisateur.getRue());
		retrait.setCodePostal(utilisateur.getCodePostal());
		retrait.setVille(utilisateur.getVille());
		articleVendu.setLieuRetrait(retrait);
		modele.addAttribute("articleVendu", articleVendu);
		modele.addAttribute("retrait",retrait);
		modele.addAttribute("utilisateur", utilisateur);
		
		
		
		return "nouvelleVente";
	}
	
	
	/***
	 * Méthode qui créer un article après envoi en formulaire 
	 * 
	 * 
	 * @param articleVendu ==> article chargé par les champs du formulaire
	 * @param bindingResult ==> Erreurs du formulaire
	 * @param model => enregistrer l'article pour l'envoyer vers la vue suivante
	 * @param principal ==> Récupérer l'utilisateur en cours qui enregistre l'article
	 * @return Chaine de caractères : vers nouvelle vente à nouveau avec les affichages des erreurs sinon vers le détail de l'article enregistré
	 * @throws BusinessException
	 */
	
	
	@PostMapping("/nouvelleVente")
	public String creerArticle(@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
			BindingResult bindingResult, Model model, Principal principal) throws BusinessException {

		Utilisateur utilisateur = utilisateurService.findUtilisateurByUsername(principal.getName()) ;
		
		articleVendu.setVendeur(utilisateur);
		
		if(bindingResult.hasErrors()) {
			return "nouvelleVente";
		}else{
			try {
				articleVendu.setPrixVente(articleVendu.getMiseAPrix());			
				articleVendu.getLieuRetrait().setArticle(articleVendu);			
				articleService.add(articleVendu);
				model.addAttribute("articleVendu",articleVendu);
				return "redirect:/detailArticle?id=" + articleVendu.getNoArticle();
			
			} catch (BusinessException e) {
			
				e.printStackTrace();
				e.getMessages().forEach(m->{
				ObjectError error = new ObjectError("globalError", m);
				bindingResult.addError(error);});
			}
			return "nouvelleVente";
		
			
		}
	}
	


	@GetMapping("/detailVente")
	public String afficherDetailArticleVente(@RequestParam("noArticle") int articleId, Model model) {
		
		
		Enchere enchereEnCours = new Enchere();
		ArticleVendu article;
		Utilisateur utilisateur;
		Retrait retrait;
		
		article = articleService.findArticleById(articleId);
		retrait = retraitService.findRetraitByArticle(articleId);

		// utilisateur est contient l'utilisateur qui a fait la plus grande offre 
		Optional<Enchere> enchere =  enchereService.findEnchereMaxById(articleId);
		if (enchere == null) {

		// utilisateur contient l'utilisateur qui a fait la plus grande offre 
	
		//if (enchereService.findEnchereMaxById(articleId).get() == null) {
			utilisateur = article.getVendeur();
		} else {
			utilisateur = utilisateurService.findUtilisateur(enchereService.findEnchereMaxById(articleId).get().getIdUtilisateur()).get();
		
		}
			
		enchereEnCours.setIdArticle(articleId);
		
		model.addAttribute("retrait", retrait);
		model.addAttribute("enchere", enchereEnCours);
		model.addAttribute("articleVendu", article);
		model.addAttribute("utilisateur", utilisateur);
		
		return "detailVente";
	}
	
	

	@PostMapping("/detailVente")
	public String recupererProposition(@ModelAttribute("enchere") Enchere enchere, Model model, Principal principal) {
		
		// Construction de l'enchere avec les paramètres en cours
		// Amélioration récupéreer id de l'utilisateur en cours plutôt que la recalculer
		
		enchere.setIdUtilisateur(utilisateurService.findUtilisateurByUsername(principal.getName()).getId());
		System.err.println("Ma proposition est de " + enchere.toString());
		
		// Traitement de la proposition d'enchere
		enchereService.ajouterEnchere(enchere);
		
		

		return "redirect:/encheres";
	}
	
	/*@GetMapping("/gagnant")
	public String affichergagnant(@RequestParam("noArticle") int articleId, Model model) {
		
		ArticleVendu article;
		article = articleService.findArticleById(articleId);
		model.addAttribute("articleVendu", article);
		return "gagnant";
	}*/
	
	
	
	@ModelAttribute("categoriesSession")// stockage de la liste des catégories en session
	public List<Categorie> chargerCategoriesEnSession(){
		
		System.out.println("appel de chargerCategoriesEnSession");
		return articleService.findAllCategories();
	}
}
