package fr.eni.projet_encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

//import fr.eni.projet_encheres.bll.dateFinEncheres;
//import fr.eni.projet_encheres.bll.nomArticle;
//import fr.eni.projet_encheres.bll.vendeur;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;


@Repository
public class ArticleDAOImpl implements ArticleDAO{

	
	private static final String FIND_ALL="SELECT * FROM ARTICLES_VENDUS";
	private static final String FIND_ARTICLE_BY_ID="SELECT * FROM ARTICLES_VENDUS WHERE no_article=:id";
	private static final String FIND_ARTICLE_BY_IDCATEGORIE="SELECT * FROM ARTICLES_VENDUS WHERE no_categorie=:id";
	private static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (:nomArticle,:description,:dateDebutEncheres,:dateFinEncheres,:miseAPrix,:prixVente,:vendeur,:categorieId);";
	private static final String UPDATE_ARTICLE_BY_ID = "UPDATE ARTICLES_VENDUS  SET nom_article=:nomArticle,description=:description,date_debut_encheres=:dateDebutEncheres, date_fin_encheres=:dateFinEncheres,prix_initial=:miseAPrix,prix_vente=:prixVente,no_utilisateur=:vendeur,no_categorie=:categorieId WHERE no_article=:id;";
	private static final String FIND_BY_NO_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur=:noUtilisateur";
	private static final String FIND_BY_NO_UTILISATEUR_DATE_EN_COURS=" SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur = :noUtilisateur AND date_debut_encheres<getdate() AND getdate()<date_fin_encheres";
	private static final String ENCHERE_TERMINEE = "SELECT CASE WHEN a.date_fin_encheres < GETDATE() THEN 1 ELSE 0 END FROM ARTICLES_VENDUS a WHERE no_article = :id;";
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UtilisateurDAO utilisateurDAO;
	private CategorieDAO categorieDAO;
	
	//constructeur
	public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UtilisateurDAO utilisateurDAO,
			CategorieDAO categorieDAO) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.utilisateurDAO = utilisateurDAO;
		this.categorieDAO = categorieDAO;
	}



		
		
	
		
		public List<ArticleVendu> findAll(){
			return this.namedParameterJdbcTemplate.query(FIND_ALL, new ArticleRowMapper(utilisateurDAO, categorieDAO));
		}
		
		

		
		/***
		 * Création d'un article en base de données
		 */
		@Override
		public void createArticle(ArticleVendu articleVendu) {
			
			MapSqlParameterSource map = new MapSqlParameterSource();	
			
			map.addValue("nomArticle", articleVendu.getNomArticle());
			map.addValue("description", articleVendu.getDescription());
			map.addValue("dateDebutEncheres", articleVendu.getDateDebutEncheres());
			map.addValue("dateFinEncheres", articleVendu.getDateFinEncheres());			
			map.addValue("miseAPrix", articleVendu.getMiseAPrix());
			map.addValue("prixVente", articleVendu.getPrixVente());
			map.addValue("vendeur", articleVendu.getVendeur().getId());
			map.addValue("categorieId", articleVendu.getCategorieArticle().getId());
		
			// pour récupérer l'id généré par la BDD :
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			this.namedParameterJdbcTemplate.update(INSERT, map,keyHolder);
			
			if(keyHolder != null && keyHolder.getKey() != null) {
				
				// mise à jour de l'instance de personne avec l'id généré par la BDD
				articleVendu.setNoArticle((int)keyHolder.getKey().intValue());
			}
		
		
		
		}



		@Override
		public List<Enchere> selectEncheresEnCours() {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public ArticleVendu selectArticleById(int id) {
			MapSqlParameterSource map=new MapSqlParameterSource();
			map.addValue("id", id);
			System.err.println("article à reprendre à choisir " + id);
			return namedParameterJdbcTemplate.queryForObject(FIND_ARTICLE_BY_ID, map, new ArticleRowMapper(utilisateurDAO, categorieDAO));
		
		}
		
		@Override
		public void updateArticle(ArticleVendu articleVendu) {
			MapSqlParameterSource map=new MapSqlParameterSource();
			map.addValue("id", articleVendu.getNoArticle());
			map.addValue("nomArticle", articleVendu.getNomArticle());
			map.addValue("description", articleVendu.getDescription());
			map.addValue("dateDebutEncheres", articleVendu.getDateDebutEncheres());
			map.addValue("dateFinEncheres", articleVendu.getDateFinEncheres());			
			map.addValue("miseAPrix", articleVendu.getMiseAPrix());
			map.addValue("prixVente", articleVendu.getPrixVente());
			map.addValue("vendeur", articleVendu.getVendeur().getId());
			map.addValue("categorieId", articleVendu.getCategorieArticle().getId());
			
			namedParameterJdbcTemplate.update(UPDATE_ARTICLE_BY_ID, map);
			
		}

		@Override
		public boolean enchereTerminee(int id) {
			MapSqlParameterSource map = new MapSqlParameterSource();
			map.addValue("id", id);
			return namedParameterJdbcTemplate.queryForObject(ENCHERE_TERMINEE, map, Boolean.class);
			
		}





		@Override
		public List<ArticleVendu> selectArticlesByCategorie(Integer categorieId) {
			MapSqlParameterSource map=new MapSqlParameterSource();
			map.addValue("id", categorieId);
			System.err.println("categorie selectionnee " + categorieId);
			return namedParameterJdbcTemplate.query(FIND_ARTICLE_BY_IDCATEGORIE, map, new ArticleRowMapper(utilisateurDAO, categorieDAO));
		}
		
		
		
		
		public List<ArticleVendu> selectArticlesByNoUtilisateur(Integer noUtilisateur){
			MapSqlParameterSource map=new MapSqlParameterSource();
			map.addValue("noUtilisateur", noUtilisateur);
			return namedParameterJdbcTemplate.query(FIND_BY_NO_UTILISATEUR, map,new ArticleRowMapper(utilisateurDAO, categorieDAO));
		}
		
		
		public List<ArticleVendu> selectArticlesByNoUtilisateurEnCours(Integer noUtilisateur){
			MapSqlParameterSource map=new MapSqlParameterSource();
			map.addValue("noUtilisateur", noUtilisateur);
			return namedParameterJdbcTemplate.query(FIND_BY_NO_UTILISATEUR_DATE_EN_COURS, map,new ArticleRowMapper(utilisateurDAO, categorieDAO));
		}
		
		
}

class ArticleRowMapper implements RowMapper<ArticleVendu>{
	
	private UtilisateurDAO utilisateurDAO;
	private CategorieDAO categorieDAO;
	

	public ArticleRowMapper(UtilisateurDAO utilisateurDAO, CategorieDAO categorieDAO) {
		super();
		this.utilisateurDAO = utilisateurDAO;
		this.categorieDAO = categorieDAO;
	}



	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {    // rs = tableau résultat de la requête
		Categorie categorie = categorieDAO.read(rs.getInt("no_categorie"));
		
		System.out.println(" Point avant erreur est : " + rs.getInt("no_utilisateur"));
		Utilisateur utilisateur= utilisateurDAO.selectUtilisateurById(rs.getInt("no_utilisateur")).get();
		utilisateur.setId(rs.getInt("no_utilisateur"));
		
		// tous les éléments qui sont dans l'article, pour créer l'article
		ArticleVendu article = new ArticleVendu(rs.getInt("no_article"), 
				rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_debut_encheres"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"),rs.getInt("prix_vente"), utilisateur, categorie);
		
		return article;
	}	
	
}

