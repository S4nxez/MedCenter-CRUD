package com.hospitalcrud.dao.repositories;

import com.hospitalcrud.dao.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PatientRepository {
    List<Patient> getAll();

    int add(Patient patient);

    void update(Patient patientDatabase);

    void delete(int idDelete, boolean confirm);
}
