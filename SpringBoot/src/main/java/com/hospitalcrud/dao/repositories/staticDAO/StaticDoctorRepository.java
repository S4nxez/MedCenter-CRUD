package com.hospitalcrud.dao.repositories.staticDAO;

import com.hospitalcrud.dao.model.Doctor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("static")
public class StaticDoctorRepository implements com.hospitalcrud.dao.repositories.DoctorRepository {
    private List<Doctor> doctorList = new ArrayList<>();

    public List<Doctor> getAll() {
        doctorList.add(new Doctor(1, "Diego", "Cardiology"));
        doctorList.add(new Doctor(2, "Pepa", "Neurology"));
        doctorList.add(new Doctor(3, "Susan", "Pediatrics"));

        return doctorList;
    }
}
