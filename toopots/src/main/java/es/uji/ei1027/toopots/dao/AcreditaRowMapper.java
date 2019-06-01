package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acredita;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AcreditaRowMapper implements RowMapper<Acredita> {
    public Acredita mapRow(ResultSet rs, int rowNum) throws SQLException {
        Acredita acredita = new Acredita();
        acredita.setIdTipoActividad(rs.getInt("idTipoActividad"));
        acredita.setIdAcreditacion(rs.getInt("idAcreditacion"));
        return acredita;
    }
}