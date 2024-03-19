package fr.eni.projet_encheres.ihm.converter;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.projet_encheres.bll.CategorieService;
import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;

@Component
public class StringToEnchereConverter implements Converter<String, Optional<Enchere>> {


		private EnchereService enchereService;
		
		public StringToEnchereConverter(EnchereService enchereService) {
			this.enchereService = enchereService;
		}
		
		@Override
		public Optional<Enchere> convert(String id) {
			System.out.println("idEnchere = " + id);
			
			return this.enchereService.findEnchereMaxById(Integer.parseInt(id));
		}

	
	
}