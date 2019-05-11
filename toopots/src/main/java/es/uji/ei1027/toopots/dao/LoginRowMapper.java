package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Login;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class LoginRowMapper implements RowMapper<Login> {
    public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
        Login login = new Login();
        login.setUsuario(rs.getString("usuario"));
        login.setContraseña(rs.getString("contraseña"));
        login.setRol(rs.getString("rol"));
        return login;
    }
}