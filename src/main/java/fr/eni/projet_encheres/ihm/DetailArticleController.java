package fr.eni.projet_encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.projet_encheres.bll.ArticleService;
import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bll.UtilisateurService;
import fr.eni.projet_encheres.bo.ArticleVendu;

@Controller
@SessionAttributes({"articleVendu"})
public class DetailArticleController {
	
	ArticleService articleService;
	UtilisateurService utilisateurService;
	EnchereService enchereService;
	

	public DetailArticleController(ArticleService articleService, UtilisateurService utilisateurService, EnchereService enchereService) {
		super();
		this.articleService = articleService;
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
	}
	
	@GetMapping("/detailArticle")
	public String afficherdetailArticle(@RequestParam("id") int id, Model modele) {
		System.out.println("identifiant de l'article " + id);
		ArticleVendu article = articleService.findArticleById(id);
		if (enchereService.findEnchereMaxById(id) != null ) {
		article.setPrixVente(enchereService.findEnchereMaxById(id).get().getMontantEnchere());}
		modele.addAttribute("article", article);
		System.out.println("On est bien dans la affichage de l' article");
		System.out.println(modele.getAttribute("article").toString());
		modele.addAttribute("vendeur");
		return "detailArticle";
	}
	
	

}
