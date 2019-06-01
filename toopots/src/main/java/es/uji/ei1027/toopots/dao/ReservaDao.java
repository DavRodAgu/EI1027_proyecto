package es.uji.ei1027.toopots.dao;


import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class ReservaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addReserva(Reserva reserva) {
        jdbcTemplate.update("INSERT INTO Reserva VALUES(nextval('reserva_idreserva_seq'), ?, ?, ?, ?, ?, ?, ?)",
                reserva.getEstadoPago(), reserva.getNumTransaccion(),
                reserva.getNumAsistentes(), reserva.getPrecioPorPersona(),
                reserva.getIdActividad(), reserva.getIdCliente(), reserva.getFecha());
    }

    public void deleteReserva(Reserva reserva) {
        jdbcTemplate.update("DELETE from Reserva where idReserva=?", reserva.getIdReserva());
    }
    
    public void deleteReserva(String idReserva) {
        jdbcTemplate.update("DELETE from Reserva where idReserva=?", Integer.valueOf(idReserva));
    }

    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE reserva SET estadoPago=?, numTransaccion=?, fecha=?, numAsistentes=?, precioPorPersona=?, idActividad=?, idCliente=? where idReserva=?",
        		reserva.getEstadoPago(), reserva.getNumTransaccion(), reserva.getFecha(),
                reserva.getNumAsistentes(), reserva.getPrecioPorPersona(), reserva.getIdActividad(),
                reserva.getIdCliente(), reserva.getIdReserva());
    }

    public Reserva getReserva(String idReserva) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Reserva WHERE idReserva=?",
                    new ReservaRowMapper(), Integer.valueOf(idReserva));
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Reserva> getReservas() {
        try {
            return jdbcTemplate.query("SELECT * from Reserva",
                    new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    
    public List<Reserva> getReservasUsuario(String usuario) {
        try {
            return jdbcTemplate.query("SELECT * from Reserva where idcliente='" + usuario + "'",
                    new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    
    public Reserva getIfReservada(String usuario, int idActividad) {
    	try {
            return jdbcTemplate.queryForObject("SELECT * from Reserva WHERE idActividad=? AND idCliente=?",
                    new ReservaRowMapper(), idActividad, usuario);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<Reserva> getNumReservasActividad(int idActividad) {
    	try {
    		return jdbcTemplate.query("SELECT * from Reserva WHERE idActividad=?",
                    new ReservaRowMapper(), idActividad);
    	} catch(EmptyResultDataAccessException e) {
    		return new ArrayList<Reserva>();
    	}
    }
}