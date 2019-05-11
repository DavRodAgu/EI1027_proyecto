package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Instructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InstructorRowMapper implements RowMapper<Instructor> {
    public Instructor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setIdInstructor(rs.getString("idInstructor"));
        instructor.setEstado(rs.getString("estado"));
        instructor.setNombre(rs.getString("nombre"));
        instructor.setEmail(rs.getString("email"));
        instructor.setIban(rs.getString("iban"));
<<<<<<< HEAD
        instructor.setFoto(rs.getString("foto"));
=======
>>>>>>> 1139f95944f94cda77751406b2360b9e6b5474b8
        return instructor;
    }
}