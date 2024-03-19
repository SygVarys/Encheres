package fr.eni.projet_encheres.ihm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet_encheres.bll.ArticleService;
import fr.eni.projet_encheres.bll.CategorieService;
import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;

@Controller
public class CategorieController {
	
	CategorieService categorieService;
	ArticleService articleService;
	EnchereService enchereService;
	
	
	//Autowired
		public CategorieController(CategorieService categorieService, ArticleService articleService, EnchereService enchereService) {
			super();
			this.categorieService = categorieService;
			this.articleService=articleService;
			this.enchereService=enchereService;
		}
	
	
		@GetMapping(path= {"/encheres","/"})
	    public String listArticles(@RequestParam(value = "categorieId", required = false) Categorie categorieId, Model model) {
			//public String listArticles(Model model) {
			List<ArticleVendu> article;
			//System.out.println("J'affiche les bons appels et les categories en cours "  + categorieId.toString());
	        System.out.println("Methode GETT " +  categorieId);
	        
	        if (categorieId != null) {	       
	        	article = (categorieId.getId() == 0)? categorieService.findAllArticles() : categorieService.findArticlesByCategorie(categorieId.getId());
	        } else {
	        	
	        	article =categorieService.findAllArticles();
	        }
	        System.out.println(article.toString());
	        model.addAttribute("articlesVendus", article);
	        List<Categorie> liste = articleService.findAllCategories();
	        model.addAttribute("categories", liste);
	        System.out.println(liste.toString());
	        return "encheres"; // nom du fichier HTML Thymeleaf sans l'extension .html
	    }

	

}
