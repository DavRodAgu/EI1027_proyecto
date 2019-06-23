package es.uji.ei1027.toopots.dao;


import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class ActividadDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActividad(Actividad actividad) {
        jdbcTemplate.update("INSERT INTO Actividad VALUES(nextval('actividad_idactividad_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                actividad.getEstado(), actividad.getNombre(),
                actividad.getDescripcion(), actividad.getDuracion(), actividad.getFecha(),
                actividad.getPrecio(), actividad.getMinAsistentes(), actividad.getMaxAsistentes(),
                actividad.getLugar(), actividad.getPuntoDeEncuentro(), actividad.getHoraDeEncuentro(),
                "", actividad.getIdTipoActividad(), actividad.getIdInstructor());
    }

    public void deleteActividad(Actividad actividad) {
        jdbcTemplate.update("DELETE from Actividad where idActividad=?", actividad.getIdActividad());
    }
    
    public void deleteActividad(String idActividad) {
        jdbcTemplate.update("DELETE from Actividad where idActividad=?", Integer.valueOf(idActividad));
    }

    public void updateActividad(Actividad actividad) {
        jdbcTemplate.update("UPDATE actividad SET estado=?, nombre=?, descripcion=?, duracion=?, fecha=?,"
        		+ " precio=?, minAsistentes=?, maxAsistentes=?, lugar=?, puntoDeEncuentro=?,"
        		+ " horaDeEncuentro=?, textoCliente=?, idTipoActividad=?, idInstructor=? where idActividad=?",
        		actividad.getEstado(), actividad.getNombre(),
                actividad.getDescripcion(), actividad.getDuracion(), actividad.getFecha(),
                actividad.getPrecio(), actividad.getMinAsistentes(), actividad.getMaxAsistentes(),
                actividad.getLugar(), actividad.getPuntoDeEncuentro(), actividad.getHoraDeEncuentro(),
                actividad.getTextoCliente(), actividad.getIdTipoActividad(), actividad.getIdInstructor(),  actividad.getIdActividad());
    }

    public Actividad getActividad(String idActividad) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Actividad WHERE idActividad=?",
                    new ActividadRowMapper(), Integer.valueOf(idActividad));
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Actividad getActividad(Actividad actividad) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Actividad WHERE nombre=? AND duracion=? AND fecha=? AND horaDeEncuentro=? AND"
            		+ " idTipoActividad=? AND idInstructor=?",
                    new ActividadRowMapper(), actividad.getNombre(), actividad.getDuracion(), actividad.getFecha(), actividad.getHoraDeEncuentro(),
                    actividad.getIdTipoActividad(), actividad.getIdInstructor());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<Actividad> getActividades() {
        try {
            return jdbcTemplate.query("SELECT * from Actividad",
                    new ActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }
    
    public List<Actividad> getActividadesByType(int idTipoActividad) {
    	try {
    		return jdbcTemplate.query("SELECT * from Actividad WHERE idTipoActividad=?",
                    new ActividadRowMapper(), idTipoActividad);
    	} catch(EmptyResultDataAccessException e) {
    		return new ArrayList<Actividad>();
    	}
    }
    
    public List<Actividad> getActividadesByInstructor(String idInstructor) {
    	try {
    		return jdbcTemplate.query("SELECT * from Actividad WHERE idInstructor=?",
                    new ActividadRowMapper(), idInstructor);
    	} catch(EmptyResultDataAccessException e) {
    		return new ArrayList<Actividad>();
    	}
    }
}
