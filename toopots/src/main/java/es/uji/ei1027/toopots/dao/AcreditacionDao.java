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
        jdbcTemplate.update("INSERT INTO Acreditacion VALUES(?, ?, ?, ?)",
                acreditacion.getIdAcreditacion(), acreditacion.getCertificado(), acreditacion.getEstado(),
                acreditacion.getIdInstructor());
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

    public Acreditacion getAcreditacion(String idAcreditacion) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Acreditacion WHERE idAcreditacion=?",
                    new AcreditacionRowMapper(), Integer.valueOf(idAcreditacion));
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Acreditacion> getAcreditaciones() {
        try {
            return jdbcTemplate.query("SELECT * from Acreditacion",
                    new AcreditacionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Acreditacion>();
        }
    }
}