package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acreditacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AcreditacionRowMapper implements RowMapper<Acreditacion> {
    public Acreditacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setIdAcreditacion(rs.getInt("idAcreditacion"));
        acreditacion.setCertificado(rs.getString("certificado"));
        acreditacion.setEstado(rs.getString("estado"));
        acreditacion.setIdInstructor(rs.getString("idInstructor"));
        return acreditacion;
    }
}