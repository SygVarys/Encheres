package fr.eni.projet_encheres.ihm.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.projet_encheres.bll.CategorieService;
import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {


		private CategorieService categorieService;
		
		public StringToCategorieConverter(CategorieService categorieService) {
			this.categorieService = categorieService;
		}
		
		@Override
		public Categorie convert(String id) {
			System.out.println("idCategorie = " + id);
			
			return this.categorieService.findCategorieById(Integer.parseInt(id));
		}

	
	
}
