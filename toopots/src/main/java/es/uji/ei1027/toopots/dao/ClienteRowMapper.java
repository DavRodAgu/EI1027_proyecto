package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClienteRowMapper implements RowMapper<Cliente> {
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getString("idCliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setEmail(rs.getString("email"));
        cliente.setSexo(rs.getString("sexo"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        return cliente;
    }
}
