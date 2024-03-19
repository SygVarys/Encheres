package fr.eni.projet_encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Retrait;

@Repository
public class RetraitDAOImpl implements RetraitDAO{

	
	private static final String FIND_ALL = "SELECT * FROM RETRAITS";
	private static final String CREATE="  INSERT INTO RETRAITS (no_article,rue,code_postal,ville) VALUES (:no_article,:rue,:code_postal,:ville)";
	private static final String FIND_RETRAITBYID = "SELECT * FROM RETRAITS WHERE no_article = :id";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private ArticleDAO articleDAO;

	
	
	//constructeur
	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ArticleDAO articleDAO) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.articleDAO = articleDAO;
	}
	
	
	
	/***
	 * Récupère tous les retraits en base de données
	 */
	public List<Retrait> findAllRetrait(){
		
		System.out.println("appel retraitDAO");
		return this.namedParameterJdbcTemplate.query(FIND_ALL, new RetraitRowMapper(articleDAO));		
	}
	
	
	/***
	 * Création d'un retrait en base de données
	 */
	@Override
	public void createRetrait (Retrait retrait) {
	
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		map.addValue("no_article", retrait.getArticle().getNoArticle());
		map.addValue("rue", retrait.getRue());
		map.addValue("code_postal", retrait.getCodePostal());
		map.addValue("ville", retrait.getVille());
		
		namedParameterJdbcTemplate.update(CREATE, map);
	}
	
	@Override
	public Retrait findRetraitByArticle(int articleId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id",articleId );
		return namedParameterJdbcTemplate.queryForObject(FIND_RETRAITBYID, mapSqlParameterSource, new RetraitRowMapper(articleDAO));
	}
	
	
	
	class RetraitRowMapper implements RowMapper<Retrait>{
		
		private ArticleDAO articleDAO;
		
		
		public RetraitRowMapper(ArticleDAO articleDAO) {
			super();
			this.articleDAO = articleDAO;
		}



		@Override
		public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ArticleVendu article = articleDAO.selectArticleById(rs.getInt("no_article"));
			
			
			return new Retrait (article,rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
			
		}
	}





	
}
