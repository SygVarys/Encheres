package fr.eni.projet_encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"utilisateur"})
public class LoginController {

		
	@GetMapping("/login")
	public String login(Model modele) {
		System.out.println(modele.getAttribute("username"));
		return "login";
	}
	/*
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, Model modele) {
		
		System.out.println("je suis là" + username);
		return "profil";
	}*/
	
}


