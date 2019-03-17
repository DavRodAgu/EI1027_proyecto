package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfertaDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void addOferta(Oferta oferta) {
		jdbcTemplate.update("INSERT INTO Oferta VALUES(?, ?)", oferta.getIdInstructor(), oferta.getIdActividad());
	}

	public void deleteOferta(Oferta oferta) {
		jdbcTemplate.update("DELETE from Oferta where idInstructor=? AND idActividad=?", oferta.getIdInstructor(),
				oferta.getIdActividad());
	}

	public Oferta getOferta(String idInstructor, int idActividad) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Oferta WHERE idInstructor=? AND idActividad=?",
					new OfertaRowMapper(), idInstructor, idActividad);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Oferta> getOfertasInstructor(String idInstructor) {
		try {
			return jdbcTemplate.query("SELECT * from Oferta WHERE idInstructor=?", new OfertaRowMapper(), idInstructor);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Oferta>();
		}
	}
	
	public List<Oferta> getOfertasActividad(int idActividad) {
		try {
			return jdbcTemplate.query("SELECT * from Oferta WHERE idActividad=?", new OfertaRowMapper(), idActividad);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Oferta>();
		}
	}

	public List<Oferta> getOfertas() {
		try {
			return jdbcTemplate.query("SELECT * from Oferta", new OfertaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Oferta>();
		}
	}
}