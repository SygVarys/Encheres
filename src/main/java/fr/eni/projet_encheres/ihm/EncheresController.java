package fr.eni.projet_encheres.ihm;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet_encheres.bll.ArticleService;
import fr.eni.projet_encheres.bll.CategorieService;
import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bll.UtilisateurService;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class EncheresController {

	EnchereService enchereService;
	CategorieService categorieService;
	ArticleService articleService;
	UtilisateurService utilisateurService;

	// Autowired
	public EncheresController(EnchereService enchereService, ArticleService articleService,
			CategorieService categorieService, UtilisateurService utilisateurService) {
		super();
		this.enchereService = enchereService;
		this.articleService = articleService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
	}

	@PostMapping("/encheres")
	public String afficherArticles(@RequestParam(value="categorieId", required = false) int categorieId,  Model model) {
		System.err.println(categorieId);
		
		
		return "redirect:/encheres?categorieId=" + categorieId;
	}

	@GetMapping("/encheresConnecte")
	public String afficherEncheresSiConnecte(
			@RequestParam(value = "categorieId", required = false) Categorie categorieId,
			@RequestParam(value = "ventesEnCours", required = false) String ventesEnCours,
			@RequestParam(value="venteNonDebutee", required = false) String venteNonDebutee, 
			@RequestParam(value="ventesTerminees", required = false) String ventesTerminees,
//			@RequestParam(value="enchOuvertes", required = false) String enchOuvertes,
//			@RequestParam(value="enchEnCours", required = false) String enchEnCours,
//			@RequestParam(value="enchRemportees", required = false) String enchRemportees,
			Model model, Principal principal) {
		// public String listArticles(Model model) {
		
		Utilisateur utilisateur = utilisateurService.findUtilisateurByUsername(principal.getName());
		List<ArticleVendu> listeVenteUtilisateurEnCours = new ArrayList<>();
		List<ArticleVendu> listeVenteUtilisateurNonDebutee = new ArrayList<>();
		List<ArticleVendu> listeVenteUtilisateurTerminees = new ArrayList<>();
		List<ArticleVendu> listeVenteUtilisateur = new ArrayList<>();
		
//		List<ArticleVendu> listeEnchOuvertes = new ArrayList<ArticleVendu>();
//		List<ArticleVendu> listeMesEnchEnCours = new ArrayList<ArticleVendu>();
//		List<ArticleVendu> listeMesEnchRemportees = new ArrayList<ArticleVendu>();
		Date today = new Date();
		
//		Optional<List<Enchere>> encheresUtilisateur = enchereService.findEncheresByUtilisateur(utilisateur.getId());
//		List<ArticleVendu> listeArtByUtilisateurEnCours = articleService.findAllArticlesByNoUtilisateurEnCours(utilisateur.getId());
//		model.addAttribute("articlesVendus",listeArtByUtilisateurEnCours);
//		System.out.println("liste articles selectionnes: "+ listeArtByUtilisateurEnCours);
		
		// System.out.println("J'affiche les bons appels et les categories en cours " +
		// categorieId.toString());
		System.out.println("Methode GET2 " + categorieId);

		
		//récupère une liste d'articles selon la catégorie passée dans le select
		List<ArticleVendu> articles;
		articles = (categorieId.getId() == 0) ? categorieService.findAllArticles()
					: categorieService.findArticlesByCategorie(categorieId.getId());
		System.out.println("liste articles par categorie :" + articles);
		model.addAttribute("articlesVendus", articles); 
		
		
		// ventes :
		// si ventesEnCours est coché
		if (ventesEnCours.contains("ventesEnCours"))     {
			listeVenteUtilisateur = articles.stream().filter(a->a.getVendeur().getId()== utilisateur.getId()).collect(Collectors.toList());
			
			listeVenteUtilisateurEnCours = listeVenteUtilisateur.stream().filter(a->a.getDateDebutEncheres().before(today)).filter(a->a.getDateFinEncheres().after(today)).collect(Collectors.toList());
			
			System.out.println("mes ventes tries en cours : " + listeVenteUtilisateurEnCours);
			//model.addAttribute("articlesVendus", listeVenteUtilisateurEnCours); 
		}
		
		// si venteNonDebutee est coché
		if (venteNonDebutee.contains("venteNonDebutee"))     {
			listeVenteUtilisateur = articles.stream().filter(a->a.getVendeur().getId()== utilisateur.getId()).collect(Collectors.toList());
							
			listeVenteUtilisateurNonDebutee = listeVenteUtilisateur.stream().filter(a->a.getDateDebutEncheres().after(today)).collect(Collectors.toList());
			
			System.out.println("mes ventes tries non debutees : " + listeVenteUtilisateurNonDebutee);
			
			//model.addAttribute("articlesVendus", listeVenteUtilisateurNonDebutee); 
		}
		
		// si ventesTerminees est coché
		if (ventesTerminees.contains("ventesTerminees"))     {
			listeVenteUtilisateur = articles.stream().filter(a->a.getVendeur().getId()== utilisateur.getId()).collect(Collectors.toList());
			
			listeVenteUtilisateurTerminees = listeVenteUtilisateur.stream().filter(a->a.getDateDebutEncheres().after(today)).collect(Collectors.toList());
		
			System.out.println("mes ventes tries terminees : " + listeVenteUtilisateurTerminees);
			//model.addAttribute("articlesVendus", listeVenteUtilisateurTerminees); 
		}
		
		
		//achats-encheres
		// si enchOuvertes est coché
//		if (enchOuvertes.contains("enchOuvertes"))     {
//			
//				listeEnchOuvertes = articles.stream().filter(a->a.getDateDebutEncheres().before(today)).filter(a->a.getDateFinEncheres().after(today)).collect(Collectors.toList());
//			}
		
		// si enchEnCours est coché
//		if (enchEnCours.contains("enchEnCours"))     {
//			
//			listeMesEnchEnCours = encheresUtilisateur.stream().filter(e->e.get    .before(today)).filter(a->a.getDateFinEncheres().after(today)).collect(Collectors.toList());
//		}
//		
		
		if (!ventesEnCours.contains("ventesEnCours")&&!venteNonDebutee.contains("venteNonDebutee")&&!ventesTerminees.contains("ventesTerminees")) {
			model.addAttribute("articlesVendus",articles);
		}else {
		
		List<ArticleVendu>listeArticlesVendus1 = Stream.concat(listeVenteUtilisateurEnCours.stream(), listeVenteUtilisateurNonDebutee.stream()).toList();
		List<ArticleVendu>listeArticlesVendus2 = Stream.concat(listeArticlesVendus1.stream(),listeVenteUtilisateurTerminees.stream()).toList();	
		model.addAttribute("articlesVendus",listeArticlesVendus2);
		System.out.println("articles en vente : " +listeArticlesVendus2);
		}
			
		
		
		List<Categorie> liste = articleService.findAllCategories();
		model.addAttribute("categories", liste);
		//System.out.println(liste.toString());
		return "/encheresConnecte";
	}

	@PostMapping("/encheresConnecte")
	public String afficherArticlesConnecte(@RequestParam("categorieId") int categorieId, 
			@RequestParam(value = "ventesEnCours", required = false) String ventesEnCours,
			@RequestParam(value="venteNonDebutee", required = false) String venteNonDebutee,
			@RequestParam(value="ventesTerminees", required = false) String ventesTerminees,
			Model model,Principal principal) {
			System.out.println("postMapping Categories" + categorieId);
			return "redirect:/encheresConnecte?categorieId=" + categorieId + "&ventesEnCours=" + ventesEnCours + "&venteNonDebutee=" + venteNonDebutee + "&ventesTerminees="+ ventesTerminees;

	}

	@ModelAttribute("categoriesSession")
	public List<Categorie> chargerCategoriesEnSession() {

		System.out.println("appel de chargerCategoriesEnSession");
		return articleService.findAllCategories();
	}

}
