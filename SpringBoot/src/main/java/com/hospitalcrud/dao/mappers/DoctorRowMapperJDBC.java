package com.hospitalcrud.dao.mappers;

import com.hospitalcrud.dao.model.Doctor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class DoctorRowMapperJDBC {
    public List<Doctor> mapRow(ResultSet rs) {

        List<Doctor> lista = new ArrayList<>();

        try {
            while (rs.next()) {

                int id = rs.getInt("doctor_id");
                String name = rs.getString("name");
                lista.add(new Doctor(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
