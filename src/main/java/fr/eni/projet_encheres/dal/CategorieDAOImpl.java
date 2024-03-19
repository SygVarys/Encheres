package fr.eni.projet_encheres.dal;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
 
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
 
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Utilisateur;
 
@Repository
public class CategorieDAOImpl implements CategorieDAO{
 
	
	private static final String FIND_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie=:no_categorie";
	private static final String FIND_ALL = "SELECT * FROM CATEGORIES";
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
		
	public CategorieDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
 
 
 
 
 
 
	// trouve une categorie selon le no_categorie
	@Override
	public Categorie read(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_categorie", id);
		System.out.println("rentre dans categorie par id" + id);
		return this.namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, map, new CategorieRowMapper());
	}
 
 
 
 
 
	// trouve toutes les catégories
	@Override
	public List<Categorie> findAll(){
		System.out.println("appel de chargerCategoriesEnSession 3");
		List<Categorie> test = this.namedParameterJdbcTemplate.query(FIND_ALL, new CategorieRowMapper());
			System.out.println("appel de chargerCategoriesEnSession 4");
			return test;
		
	}
 
	
	
	
}

class CategorieRowMapper implements RowMapper<Categorie>{

	@Override
	public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		Categorie	 categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
		
		return categorie;
	}
	
	
	
	
}

 