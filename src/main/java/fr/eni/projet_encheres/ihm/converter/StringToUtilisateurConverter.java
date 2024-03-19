package fr.eni.projet_encheres.ihm.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.projet_encheres.bll.EnchereService;
import fr.eni.projet_encheres.bll.UtilisateurService;
import fr.eni.projet_encheres.bo.Utilisateur;


@Component
public class StringToUtilisateurConverter implements Converter<String, Utilisateur>{


	private UtilisateurService utilisateurService;
	
	public StringToUtilisateurConverter(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	
	@Override
	public Utilisateur convert(String id) {
		System.out.println("idUtilisateur = " + id);
		
		return this.utilisateurService.findUtilisateur(Integer.parseInt(id)).get();
	}

}
	
	
	
	
	


