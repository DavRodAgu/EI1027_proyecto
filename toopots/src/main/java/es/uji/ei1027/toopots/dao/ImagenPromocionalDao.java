package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.ImagenPromocional;

@Repository
public class ImagenPromocionalDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void addImagenPromocional(ImagenPromocional imagenPromocional) {
		jdbcTemplate.update("INSERT INTO ImagenPromocional VALUES(nextval('imagenpromocional_idimagen_seq'), ?, ?)", 
				imagenPromocional.getImagen(),
				imagenPromocional.getIdActividad());
	}

	public void deleteImagenPromocional(ImagenPromocional imagenPromocional) {
		jdbcTemplate.update("DELETE from ImagenPromocional where idImagen=?", 
				imagenPromocional.getIdImagen());
	}

	public void deleteImagenPromocional(String idImagen) {
		jdbcTemplate.update("DELETE from ImagenPromocional where idImagen=?", 
				Integer.valueOf(idImagen));
	}

	public void updateImagenPromocional(ImagenPromocional imagenPromocional) {
		jdbcTemplate.update("UPDATE actividad SET imagen=?, idActividad=?", 
				imagenPromocional.getImagen(),
				imagenPromocional.getIdActividad());
	}

	public ImagenPromocional getImagenPromocional(String idImagen) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from ImagenPromocional WHERE idImagen=?",
					new ImagenPromocionalRowMapper(), Integer.valueOf(idImagen));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<ImagenPromocional> getImagenesPromocionales() {
		try {
			return jdbcTemplate.query("SELECT * from ImagenPromocional", new ImagenPromocionalRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<ImagenPromocional>();
		}
	}
	
	public ImagenPromocional getImagen(String idActividad) {
		try {
			return jdbcTemplate.query("SELECT * from ImagenPromocional WHERE idActividad=?",
					new ImagenPromocionalRowMapper(), Integer.valueOf(idActividad)).get(0);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
