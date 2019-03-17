package es.uji.ei1027.toopots.dao;


import es.uji.ei1027.toopots.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class InstructorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInstructor(Instructor instructor) {
        jdbcTemplate.update("INSERT INTO Instructor VALUES(?, ?, ?, ?, ?)",
                instructor.getIdInstructor(), instructor.getEstado(), instructor.getNombre(),
                instructor.getEmail(), instructor.getIban());
    }

    public void deleteInstructor(Instructor instructor) {
        jdbcTemplate.update("DELETE from Instructor where idInstructor=?", instructor.getIdInstructor());
    }
    
    public void deleteInstructor(String idInstructor) {
        jdbcTemplate.update("DELETE from Instructor where idInstructor=?", idInstructor);
    }

    public void updateInstructor(Instructor instructor) {
        jdbcTemplate.update("UPDATE instructor SET estado=?, nombre=?, email=?, iban=? where idInstructor=?",
        		instructor.getEstado(), instructor.getNombre(), instructor.getEmail(),
                instructor.getIban(), instructor.getIdInstructor());
    }

    public Instructor getInstructor(String idInstructor) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Instructor WHERE idInstructor=?",
                    new InstructorRowMapper(), idInstructor);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Instructor> getInstructores() {
        try {
            return jdbcTemplate.query("SELECT * from Instructor",
                    new InstructorRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Instructor>();
        }
    }
}