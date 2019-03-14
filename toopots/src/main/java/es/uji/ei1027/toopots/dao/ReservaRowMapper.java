package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ReservaRowMapper implements RowMapper<Reserva> {
    public Reserva mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("idReserva"));
        reserva.setEstadoPago(rs.getString("estadoPago"));
        reserva.setNumTransaccion(rs.getInt("numTransaccion"));
        reserva.setFecha(rs.getDate("fecha"));
        reserva.setNumAsistentes(rs.getInt("numAsistentes"));
        reserva.setPrecioPorPersona(rs.getFloat("precioPorPersona"));
        reserva.setIdActividad(rs.getInt("idActividad"));
        reserva.setIdCliente(rs.getString("idCliente"));
        return reserva;
    }
}