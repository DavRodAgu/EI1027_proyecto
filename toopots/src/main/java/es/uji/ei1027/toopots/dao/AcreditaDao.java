package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acredita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AcreditaDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void addAcredita(Acredita acredita) {
		jdbcTemplate.update("INSERT INTO Acredita VALUES(?, ?)", acredita.getIdTipoActividad(),
				acredita.getIdAcreditacion());
	}

	public void deleteAcredita(Acredita acredita) {
		jdbcTemplate.update("DELETE from Acredita where idTipoActividad=? AND idAcreditacion=?",
				acredita.getIdTipoActividad(), acredita.getIdAcreditacion());
	}

	public Acredita getAcredita(int idTipoActividad, int idAcreditacion) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Acredita WHERE idTipoActividad=? AND idAcreditacion=?",
					new AcreditaRowMapper(), idTipoActividad, idAcreditacion);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Acredita> getAcreditaciones() {
		try {
			return jdbcTemplate.query("SELECT * from Acredita", new AcreditaRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Acredita>();
		}
	}
}