package es.uji.ei1027.toopots.dao;


import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class ClienteDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCliente(Cliente cliente) {
        jdbcTemplate.update("INSERT INTO Cliente VALUES(?, ?, ?, ?, ?)",
                cliente.getIdCliente(), cliente.getNombre(), cliente.getEmail(),
                cliente.getSexo(),cliente.getFechaNacimiento());
    }

    public void deleteCliente(Cliente cliente) {
        jdbcTemplate.update("DELETE from Cliente where idCliente=?", cliente.getIdCliente());
    }
    
    public void deleteCliente(String idCliente) {
        jdbcTemplate.update("DELETE from Cliente where idCliente=?", idCliente);
    }

    public void updateCliente(Cliente cliente) {
        jdbcTemplate.update("UPDATE cliente SET nombre=?, email=?, sexo=?, fechaNacimiento=? where idCliente=?",
        		cliente.getNombre(), cliente.getEmail(), cliente.getSexo(),
                cliente.getFechaNacimiento(), cliente.getIdCliente());
    }

    public Cliente getCliente(String idCliente) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Cliente WHERE idCliente=?",
                    new ClienteRowMapper(), idCliente);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Cliente> getClientes() {
        try {
            return jdbcTemplate.query("SELECT * from Cliente",
                    new ClienteRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Cliente>();
        }
    }
}
