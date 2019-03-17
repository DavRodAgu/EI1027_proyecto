package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Prefiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrefiereDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void addPrefiere(Prefiere prefiere) {
		jdbcTemplate.update("INSERT INTO Prefiere VALUES(?, ?)", prefiere.getIdTipoActividad(),
				prefiere.getIdCliente());
	}

	public void deletePrefiere(Prefiere prefiere) {
		jdbcTemplate.update("DELETE from Prefiere where idTipoActividad=? AND idCliente=?",
				prefiere.getIdTipoActividad(), prefiere.getIdCliente());
	}

	public void deletePrefiere(int idTipoActividad, String idCliente) {
		jdbcTemplate.update("DELETE from Prefiere where idTipoActividad=? AND idCliente=?", idTipoActividad, idCliente);
	}

	public Prefiere getPrefiere(int idTipoActividad, String idCliente) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Prefiere WHERE idTipoActividad=? AND idCliente=?",
					new PrefiereRowMapper(), idTipoActividad, idCliente);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Prefiere> getPreferenciasCliente(String idCliente) {
		try {
			return jdbcTemplate.query("SELECT * from Prefiere WHERE idCliente=?", new PrefiereRowMapper(), idCliente);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Prefiere>();
		}
	}

	public List<Prefiere> getPreferencias() {
		try {
			return jdbcTemplate.query("SELECT * from Prefiere", new PrefiereRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Prefiere>();
		}
	}
}