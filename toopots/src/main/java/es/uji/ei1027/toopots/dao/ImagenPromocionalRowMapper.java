package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.ImagenPromocional;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagenPromocionalRowMapper implements RowMapper<ImagenPromocional>{

	@Override
	public ImagenPromocional mapRow(ResultSet rs, int rowNum) throws SQLException {
		ImagenPromocional imagenPromocional = new ImagenPromocional();
		imagenPromocional.setIdImagen(rs.getInt("idImagen"));
		imagenPromocional.setImagen(rs.getString("imagen"));
		imagenPromocional.setIdActividad(rs.getInt("idActividad"));
		return imagenPromocional;
	}

}
