package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Instructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InstructorRowMapper implements RowMapper<Instructor> {
    public Instructor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Instructor instructor =new Instructor();
        instructor.setIdInstructor(rs.getString("idInstructor"));
        instructor.setEstado(rs.getString("estado"));
        instructor.setNombre(rs.getString("nombre"));
        instructor.setEmail(rs.getString("email"));
        instructor.setIban(rs.getString("iban"));
        return instructor;
    }
}