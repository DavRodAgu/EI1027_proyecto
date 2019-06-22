package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acreditacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AcreditacionDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void addAcreditacion(Acreditacion acreditacion) {
		jdbcTemplate.update("INSERT INTO Acreditacion VALUES(nextval('acreditacion_idacreditacion_seq'), ?, ?, ?)",
				acreditacion.getCertificado(), acreditacion.getEstado(), acreditacion.getIdInstructor());
	}

	public void deleteAcreditacion(Acreditacion acreditacion) {
		jdbcTemplate.update("DELETE from Acreditacion where idAcreditacion=?", acreditacion.getIdAcreditacion());
	}

	public void deleteAcreditacion(String idAcreditacion) {
		jdbcTemplate.update("DELETE from Acreditacion where idAcreditacion=?", Integer.valueOf(idAcreditacion));
	}

	public void updateAcreditacion(Acreditacion acreditacion) {
		jdbcTemplate.update("UPDATE acreditacion SET certificado=?, estado=?, idInstructor=? where idAcreditacion=?",
				acreditacion.getCertificado(), acreditacion.getEstado(), acreditacion.getIdInstructor(),
				acreditacion.getIdAcreditacion());
	}

	public void rechazaAcreditacion(int idAcreditacion) {
		jdbcTemplate.update("UPDATE acreditacion SET estado=? where idAcreditacion=?", "rechazada", idAcreditacion);
	}

	public void aceptaAcreditacion(int idAcreditacion) {
		jdbcTemplate.update("UPDATE acreditacion SET estado=? where idAcreditacion=?", "aceptada", idAcreditacion);
	}

	public Acreditacion getAcreditacion(int idAcreditacion) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Acreditacion WHERE idAcreditacion=?",
					new AcreditacionRowMapper(), idAcreditacion);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Acreditacion> getAcreditaciones() {
		try {
			return jdbcTemplate.query("SELECT * from Acreditacion", new AcreditacionRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Acreditacion>();
		}
	}
	
	public List<Acreditacion> getAcreditacionByIdInstructor(String idInstructor) {
		try {
			return jdbcTemplate.query("SELECT * from Acreditacion WHERE idInstructor=?",
					new AcreditacionRowMapper(), idInstructor);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Acreditacion>();
		}
	}
	
	public Acreditacion getAcreditacion(String idInstructor, String certificado) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Acreditacion WHERE idInstructor=? AND certificado=?",
					new AcreditacionRowMapper(), idInstructor, certificado);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}