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
import org.springframework.stereotype.Repository;

import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;

@Repository
public class EnchereDAOImpl  implements EnchereDAO{
	
	
	//private final String SELECT_ENCHERES_BY_UTILISATEUR = "SELECT * FROM ENCHERES e JOIN ARTICLES_VENDUS a ON e.no_utilisateur= a.no_utilisateur WHERE e.no_utilisateur=:noUtilisateur";

	private final String SELECT_MAX_ENCHERE_BY_ID_ARTICLE = "SELECT  * FROM ENCHERES WHERE (no_article=:id AND montant_enchere=(SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article=:id))";
	
	private final String SELECT_ENCHERE_BY_ID = "SELECT * FROM ENCHERES WHERE no_utilisateur = :Id";

	private final String INSERT_INTO_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere ) VALUES (:idUtilisateur, :idArticle, GETDATE(), :montant)";
	
	private final String SELECT_TIME_BETWEEN_ENCHERES = "  SELECT DATEDIFF(s,date_fin_encheres , GETDATE())FROM ARTICLES_VENDUS WHERE no_article =:id;";

	private final String SELECT_ENCHERES_EN_COURS_BY_ID ="SELECT e.no_utilisateur, a.no_article,e.date_enchere,e.montant_enchere  FROM ENCHERES e JOIN ARTICLES_VENDUS a ON a.no_article = e.no_article JOIN UTILISATEURS u ON u.no_utilisateur = e.no_utilisateur WHERE (a.no_utilisateur = :id AND GETDATE() < a.date_fin_encheres AND GETDATE() >= a.date_debut_encheres) ORDER BY a.no_article, e.montant_enchere DESC";
	
	
	private UtilisateurDAO utilisateurDAO;
	private CategorieDAO categorieDAO;
	private ArticleDAO articleDAO;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public EnchereDAOImpl(UtilisateurDAO utilisateurDAO, CategorieDAO categorieDAO, ArticleDAO articleDAO,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.utilisateurDAO = utilisateurDAO;
		this.categorieDAO = categorieDAO;
		this.articleDAO = articleDAO;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
	public Optional<Enchere> selectEnchereMax(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id",id);
		System.out.println("Le numéro d'enchere est " + id);
		
		try {
			return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_MAX_ENCHERE_BY_ID_ARTICLE, map, new EnchereRowMapper()));
		} 
		catch (EmptyResultDataAccessException e) {
		   System.err.println(e.getMessage());
		   }
		   return null;
		
	
	}


	@Override
	public Boolean compareToMax(int idArticle, int proposition) {
		
		return null;
	}


	@Override
	public void insertEnchere(Enchere enchere) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUtilisateur",enchere.getIdUtilisateur());
		map.addValue("idArticle",enchere.getIdArticle());
		//map.addValue("date",enchere.getDateEnchere());
		map.addValue("montant", enchere.getMontantEnchere());
		namedParameterJdbcTemplate.update(INSERT_INTO_ENCHERE, map);
		
	}


	@Override
	public Enchere selectEnchereById(int parseInt) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("Id", parseInt);
		return namedParameterJdbcTemplate.queryForObject(SELECT_ENCHERE_BY_ID,mapSqlParameterSource, new EnchereRowMapper() );
	}


	@Override
	public boolean enchereTerminee(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		
		return namedParameterJdbcTemplate.queryForObject(INSERT_INTO_ENCHERE, map, Integer.class)>0;
	}
	
	
	@Override
	public Optional<List<Enchere>> listeEncheresparId(int id){
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		return Optional.ofNullable(namedParameterJdbcTemplate.query(SELECT_ENCHERES_EN_COURS_BY_ID, map, new EnchereRowMapper()));
				
	}


//	@Override
//	public Optional<List<Enchere>> listeEnchereByUtilisateur(int noUtilisateur) {
//		MapSqlParameterSource map = new MapSqlParameterSource();
//		map.addValue("id", noUtilisateur);
//		return Optional.ofNullable(namedParameterJdbcTemplate.query(SELECT_ENCHERES_BY_UTILISATEUR, map, new EnchereRowMapper()));
//	}

}

class EnchereRowMapper implements RowMapper<Enchere>{

	@Override
	public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		Enchere	 enchere = new Enchere(rs.getInt("no_utilisateur"), rs.getInt("no_article"), rs.getDate("date_enchere"),rs.getInt("montant_enchere"));
		
		return enchere;
	}
	
	
	
	
}

 
