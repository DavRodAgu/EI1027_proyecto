package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Oferta;

public final class OfertaRowMapper implements RowMapper<Oferta> {
	public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Oferta oferta = new Oferta();
		oferta.setIdInstructor(rs.getString("idInstructor"));
		oferta.setIdActividad(rs.getInt("idActividad"));
		return oferta;
	}
}
