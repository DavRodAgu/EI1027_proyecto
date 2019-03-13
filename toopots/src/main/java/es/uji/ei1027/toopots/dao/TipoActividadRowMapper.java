package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TipoActividadRowMapper implements RowMapper<TipoActividad> {
    public TipoActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoActividad tipoActividad = new TipoActividad();
        tipoActividad.setIdTipoActividad(rs.getInt("idTipoActividad"));
        tipoActividad.setNombre(rs.getString("nombre"));
        tipoActividad.setNivel(rs.getString("nivel"));
        return tipoActividad;
    }
}
