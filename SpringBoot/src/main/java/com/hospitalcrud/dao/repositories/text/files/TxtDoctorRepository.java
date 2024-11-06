package com.hospitalcrud.dao.repositories.text.files;

import com.hospitalcrud.config.Configuration;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.utils.Constantes;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
@Log4j2
@Profile("txtFiles")
public class TxtDoctorRepository implements com.hospitalcrud.dao.repositories.DoctorRepository {


    public TxtDoctorRepository() {
    }

    @Override
    public List<Doctor> getAll() { //PONER EL READALL LINES COMO EN EL PATIENT QUE ESTE ES MAS CORTO
        List<Doctor> doctors = new ArrayList<>();

        try {
            List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths
                    .get(Configuration.getInstance().getProperty(Constantes.DOCTOR_PATH)));
            for (String line : lines) {
                String[] values = line.split(";");
                Doctor doctor = new Doctor(
                        Integer.parseInt(values[0].trim()),
                        values[1].trim()
                );

                doctors.add(doctor);
                updateNextId(doctor.getId() + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    private void updateNextId(int nextId) {
        Configuration.getInstance().setProperty(Constantes.NEXT_DOCTOR_ID, String.valueOf(nextId));
    }

}
