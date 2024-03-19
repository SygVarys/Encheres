package fr.eni.projet_encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.exception.BusinessException;

/****
 * 
 * DAO concernant les liens avec la table des utilisateurs
 * 
 * 
 */

/**
 * 
 */
@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	/**
	 * Requetes pour récupérer en base selon id de l'utilisateur ou son pseudo
	 * Requêtes pour insérer et mettre à jour l'utilisateur Requête de comptage des
	 * utilisateurs pour tester si déjà inscrit par son pseudo ou son email : clé
	 * secondaire Requête pour effacer un utilisateur
	 * 
	 */

	private static final String SELECT_UTILISATEUR_BY_ID = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit FROM UTILISATEURS WHERE no_utilisateur=:id";
	private static final String SELECT_UTILISATEUR_BY_USERNAME = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit FROM UTILISATEURS WHERE pseudo=:pseudo";

	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur ) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe, 0, 0 )";
	private static final String UPDATE_UTILISATEUR_BY_PSEUDO = "UPDATE UTILISATEURS SET nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, rue=:rue, code_postal=:code_postal, ville=:ville, mot_de_passe=:mot_de_passe, credit=:credit WHERE pseudo=:pseudo";

	private static final String COUNT_UTILISATEUR_BY_PSEUDO = "SELECT count(*) FROM UTILISATEURS WHERE pseudo=:pseudo";
	private static final String COUNT_UTILISATEUR_BY_EMAIL = "SELECT count(*) FROM UTILISATEURS WHERE email=:email";

	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE pseudo=:pseudo";
	private static final String COUNT_ENCHERES_EN_COURS_BY_ID = "SELECT count(*) FROM ARTICLES_VENDUS a JOIN UTILISATEURS u ON u.no_utilisateur = a.no_utilisateur WHERE u.pseudo=:pseudo AND DateDiff(dd, GETDATE(), date_fin_encheres)>=0";

	private static final String SELECT_ENCHERES_BY_PSEUDO = "SELECT * FROM ENCHERES e JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur WHERE pseudo=:u.pseudo";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/***
	 * 
	 * Selection d'un utilisateur par son id Optional<Utilisateur> si non trouvé
	 * 
	 * 
	 */
	@Override
	public Optional<Utilisateur> selectUtilisateurById(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		System.out.println("J'essaie bien de reconnaitre un utilisateur");
		try {
			return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_UTILISATEUR_BY_ID, map,
					new UtilisateurRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/***
	 * Création d'un utilisateur en base de données
	 * 
	 * Mappage d'un utilisateur et création de son id
	 * 
	 */

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {

		MapSqlParameterSource map = new MapSqlParameterSource();

		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("code_postal", utilisateur.getCodePostal());
		map.addValue("ville", utilisateur.getVille());
		map.addValue("mot_de_passe", utilisateur.getMotDePasse());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(INSERT_UTILISATEUR, map, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {
			int idUser = keyHolder.getKey().intValue();
			utilisateur.setId(idUser);
		}
	}

	/**
	 * 
	 * Méthode pour sélectionner un utilisateur par son pseudo
	 * 
	 * 
	 */

	@Override
	public Utilisateur selectUtilisateurByUsername(String pseudo) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);
		System.out.println("J'essaie bien de reconnaitre un utilisateur par son pseudo" + pseudo);
		return namedParameterJdbcTemplate.queryForObject(SELECT_UTILISATEUR_BY_USERNAME, map,
				new UtilisateurRowMapper());

	}

	/**
	 * 
	 * Méthode pour supprimer un utilisateur par son pseudo Supprime le compte sauf
	 * si des ventes sont encore en cours
	 * 
	 */
	@Override
	public void deleteUtilisateur(String pseudo) throws BusinessException {
		BusinessException e = new BusinessException();
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);
		int nbObjetsEnVente = namedParameterJdbcTemplate.queryForObject(COUNT_ENCHERES_EN_COURS_BY_ID, map,
				Integer.class);

		if (nbObjetsEnVente == 0) {
			namedParameterJdbcTemplate.update(DELETE_UTILISATEUR, map);
		} else {
			e.add("Vous ne pouvez supprimer votre compte car vous avez encore des objets en vente");
			throw e;
		}

	}

	/**
	 * 
	 * 
	 */
	@Override
	public int countUtilisateurByEmail(String email) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("email", email);
		return namedParameterJdbcTemplate.queryForObject(COUNT_UTILISATEUR_BY_EMAIL, map, Integer.class);
	}

	@Override
	public int countUtilisateurByPseudo(String pseudo) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);
		return namedParameterJdbcTemplate.queryForObject(COUNT_UTILISATEUR_BY_PSEUDO, map, Integer.class);
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
		System.out.println("DANS LA DAO, l'utilisateur est : ------------------" + utilisateur.toString());
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("code_postal", utilisateur.getCodePostal());
		map.addValue("ville", utilisateur.getVille());
		map.addValue("mot_de_passe", utilisateur.getMotDePasse());
		map.addValue("credit", utilisateur.getCredit());

		namedParameterJdbcTemplate.update(UPDATE_UTILISATEUR_BY_PSEUDO, map);

	}

	@Override
	public List<Enchere> selectEnchereByUsername(String pseudo) {

		return namedParameterJdbcTemplate.query(SELECT_ENCHERES_BY_PSEUDO, new BeanPropertyRowMapper<>(Enchere.class));
	}

}

class UtilisateurRowMapper implements RowMapper<Utilisateur> {

	@Override
	public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {

		Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"),
				rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"),
				rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"), rs.getString("mot_de_passe"),
				rs.getInt("credit"));

		return utilisateur;
	}

}
