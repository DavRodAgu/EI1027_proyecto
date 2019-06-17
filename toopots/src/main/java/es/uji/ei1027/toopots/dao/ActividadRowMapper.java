package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalTime;
import java.time.LocalDate;

public final class ActividadRowMapper implements RowMapper<Actividad> {
    public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actividad actividad = new Actividad();
        actividad.setIdActividad(rs.getInt("idActividad"));
        actividad.setEstado(rs.getString("estado"));
        actividad.setNombre(rs.getString("nombre"));
        actividad.setDescripcion(rs.getString("descripcion"));
        actividad.setDuracion(rs.getObject("duracion", LocalTime.class));
        // actividad.setFecha(rs.getDate("fecha"));
        actividad.setFecha(rs.getObject("fecha", LocalDate.class));
        actividad.setPrecio(rs.getFloat("precio"));
        actividad.setMinAsistentes(rs.getInt("minAsistentes"));
        actividad.setMaxAsistentes(rs.getInt("maxAsistentes"));
        actividad.setLugar(rs.getString("lugar"));
        actividad.setPuntoDeEncuentro(rs.getString("puntoDeEncuentro"));
        // actividad.setHoraDeEncuentro(rs.getTime("horaDeEncuentro"));
        // System.out.println(rs.getObject("horaDeEncuentro").getClass());
        actividad.setHoraDeEncuentro(rs.getObject("horaDeEncuentro", LocalTime.class));
        actividad.setTextoCliente(rs.getString("textoCliente"));
        actividad.setIdTipoActividad(rs.getInt("idTipoActividad"));
        actividad.setIdInstructor(rs.getString("idInstructor"));
        return actividad;
    }
}