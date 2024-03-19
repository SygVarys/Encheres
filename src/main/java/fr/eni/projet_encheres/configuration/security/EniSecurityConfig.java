package fr.eni.projet_encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
public class EniSecurityConfig {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	/**
	 * 
	 * Création du bean Password encoder
	 */
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	
	
	public EniSecurityConfig(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
	// pour authentification : pour aller chercher les infos en BDD
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		System.out.println("Ca passe par là");
	
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe,1 FROM UTILISATEURS WHERE pseudo=?");//"1" car pas d'info "enabled" dans notre table, donc donné à 1 automatiquement
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role FROM UTILISATEURS u  JOIN ROLES r ON u.administrateur = r.administrateur WHERE pseudo=?");	
		
		System.out.println(jdbcUserDetailsManager.toString());
		return jdbcUserDetailsManager;
	}
	
	/*
	@Bean
	public DaoAuthenticationProvider authProvider(DataSource dataSource) {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsManager(dataSource));
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}*/

	
	//annalyse demande envoyée par le client
		@Bean
		SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			
			http.authorizeHttpRequests(auth-> 
					auth.requestMatchers(HttpMethod.GET,"/").permitAll()
					.requestMatchers(HttpMethod.GET,"/encheres").permitAll()
					.requestMatchers(HttpMethod.GET,"/inscription").permitAll()
					.requestMatchers(HttpMethod.GET,"/detailArticle").permitAll()
					.requestMatchers(HttpMethod.GET,"/encheresConnecte").hasAnyRole("INSCRIT","ADMIN")
					.requestMatchers(HttpMethod.GET, "/profil").hasAnyRole("INSCRIT","ADMIN")
					.requestMatchers(HttpMethod.GET, "/vendeur").hasAnyRole("INSCRIT","ADMIN")
					.requestMatchers(HttpMethod.GET, "/modifProfil").hasAnyRole("INSCRIT","ADMIN")
					.requestMatchers(HttpMethod.GET, "/nouvelleVente").hasAnyRole("INSCRIT","ADMIN")
					/*.requestMatchers(HttpMethod.GET,"/profil").hasRole("INSCRIT")
					.requestMatchers(HttpMethod.POST,"/films/creer").hasRole("ADMIN")*/
					.requestMatchers("/*").permitAll()
					.requestMatchers("/css/*").permitAll()
					.requestMatchers("/*", "/css/**", "/image/**", "/marteau", "/error").permitAll()
					.requestMatchers("/javascript/*").permitAll()
					/*.anyRequest().denyAll()*/
			);
			
						
			
			//utilisation de la page de login créée
			http.formLogin(form->{
				form.loginPage("/login").permitAll();//vérifie que la personne est bien en BDD
				form.toString();
				form.defaultSuccessUrl("/encheres"); // si ok -> accueil, sinon revient à login
					
			});
			
			
			//gestion du logout -> supprime session coté serveur, supprimer cookies, supprimer champs saisis lors du login, url de deconnexion, quoi faire après déconnexion + affichage confirmation deconnexion, permission pour la déconnexion (permetAll)
			http.logout(logout->
				logout.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/encheres")
				.permitAll()
			);
			
			
			
			return http.build();
		}
}
