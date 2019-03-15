package es.uji.ei1027.toopots.dao;


import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class TipoActividadDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("INSERT INTO TipoActividad VALUES(nextval('tipoactividad_idtipoactividad_seq'), ?, ?)",
                tipoActividad.getNombre(), tipoActividad.getNivel());
    }

    public void deleteTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("DELETE from TipoActividad where idTipoActividad=?", tipoActividad.getIdTipoActividad());
    }
    
    public void deleteTipoActividad(String idTipoActividad) {
        jdbcTemplate.update("DELETE from TipoActividad where idTipoActividad=?", Integer.valueOf(idTipoActividad));
    }

    public void updateTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("UPDATE tipoActividad SET nombre=?, nivel=? where idTipoActividad=?",
        		tipoActividad.getNombre(), tipoActividad.getNivel(), tipoActividad.getIdTipoActividad());
    }

    public TipoActividad getTipoActividad(String idTipoActividad) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from TipoActividad WHERE idTipoActividad=?",
                    new TipoActividadRowMapper(), Integer.valueOf(idTipoActividad));
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TipoActividad> getTipoActividades() {
        try {
            return jdbcTemplate.query("SELECT * from TipoActividad",
                    new TipoActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<TipoActividad>();
        }
    }
}
