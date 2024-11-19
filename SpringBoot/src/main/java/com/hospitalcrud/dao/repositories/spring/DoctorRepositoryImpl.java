package com.hospitalcrud.dao.repositories.spring;

import com.hospitalcrud.dao.mappers.DoctorRowMapper;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("springJdbc")
public class DoctorRepositoryImpl implements DoctorRepository {

    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private DoctorRowMapper doctorRowMapper;

    @Override
    public List<Doctor> getAll() {
       return jdbcClient.sql("select * from doctors")
               .query(Doctor.class)
               .list();
    }
}
