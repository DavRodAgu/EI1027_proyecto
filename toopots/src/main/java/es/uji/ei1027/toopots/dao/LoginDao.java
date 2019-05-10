package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LoginDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Login getLogin(String usuario, String contraseña) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Login WHERE usuario=? AND contraseña=?",
					new LoginRowMapper(), usuario, contraseña);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
