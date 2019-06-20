package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.Comentario;

@Repository
public class ComentarioDao {
	
	 private JdbcTemplate jdbcTemplate;

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	        jdbcTemplate = new JdbcTemplate(dataSource);
	    }

	    public void addComentario(Comentario comentario) {
	        jdbcTemplate.update("INSERT INTO Comentario VALUES(nextval('comentario_idcomentario_seq'), ?, ?, ?, ?)",
	                comentario.getComentario(),
	                comentario.getValoracion(), comentario.getIdCliente(),
	                comentario.getIdActividad());
	    }

	    public void deleteComentario(Comentario comentario) {
	        jdbcTemplate.update("DELETE from Comentario where idComentario=?", comentario.getIdComentario());
	    }
	    
	    public void deleteComentario(String idComentario) {
	        jdbcTemplate.update("DELETE from Comentario where idComentario=?", Integer.valueOf(idComentario));
	    }

	    public void updateComentario(Comentario comentario) {
	        jdbcTemplate.update("UPDATE actividad SET comentario=?, valoracion=?, ",
	        		comentario.getComentario(), comentario.getValoracion());
	    }

	    public Comentario getComentario(String idComentario) {
	        try {
	            return jdbcTemplate.queryForObject("SELECT * from Comentario WHERE idComentario=?",
	                    new ComentarioRowMapper(), Integer.valueOf(idComentario));
	        }
	        catch(EmptyResultDataAccessException e) {
	            return null;
	        }
	    }

	    public List<Comentario> getComentarios() {
	        try {
	            return jdbcTemplate.query("SELECT * from Comentario",
	                    new ComentarioRowMapper());
	        }
	        catch(EmptyResultDataAccessException e) {
	            return new ArrayList<Comentario>();
	        }
	    }
}
