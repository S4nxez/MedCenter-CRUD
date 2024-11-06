package com.hospitalcrud.dao.repositories.staticDAO;

import com.hospitalcrud.dao.model.Doctor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("static")
public class StaticDoctorRepository implements com.hospitalcrud.dao.repositories.DoctorRepository {
    private List<Doctor> doctorList=new ArrayList<>();

    public List<Doctor> getAll(){
        doctorList.add(new Doctor(1,"Diego"));
        doctorList.add(new Doctor(1,"Pepa"));
        doctorList.add(new Doctor(1,"Susan"));

        return doctorList;
    }
}
