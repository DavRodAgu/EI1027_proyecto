package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Prefiere;

public final class PrefiereRowMapper implements RowMapper<Prefiere> {
	public Prefiere mapRow(ResultSet rs, int rowNum) throws SQLException {
		Prefiere prefiere = new Prefiere();
		prefiere.setIdTipoActividad(rs.getInt("idTipoActividad"));
		prefiere.setIdCliente(rs.getString("idCliente"));
		return prefiere;
	}
}
