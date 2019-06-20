package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Comentario;

public class ComentarioRowMapper implements  RowMapper<Comentario>{

	@Override
	public Comentario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comentario comentario = new Comentario();
        comentario.setIdComentario(rs.getInt("idComentario"));
        comentario.setComentario(rs.getString("comentario"));
        comentario.setValoracion(rs.getInt("valoracion"));
        comentario.setIdCliente(rs.getString("idCliente"));
        comentario.setIdActividad(rs.getInt("idActividad"));
        return comentario;
	}
}
