package com.hospitalcrud.dao.repositories.staticDAO;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repositories.PatientRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("static")
public class StaticPatientRepository implements PatientRepository {

    private static List<Patient> patients = new ArrayList<>();

    public StaticPatientRepository() {
        patients.add(new Patient(
                LocalDate.of(2003, 5, 23),
                new Credential("john_smith", "password123", 1),
                1,
                "John Smith",
                "333-222-111"
        ));

        patients.add(new Patient(
                LocalDate.of(2003, 5, 23),
                new Credential("diego_smith", "password456", 2),
                2,
                "Diego Smith",
                "333-222-111"
        ));

        patients.add(new Patient(
                LocalDate.of(2003, 5, 23),
                new Credential("maksim_smith", "password789", 3),
                3,
                "Maksim Smith",
                "333-222-111"
        ));
    }

    @Override
    public List<Patient> getAll() {
        return patients;
    }

    public int add(Patient patient) {
        patients.add(patient);
        return 4;
    }

    public void update(Patient patientDatabase) {
    patients.replaceAll(p -> p.getId() == patientDatabase.getId() ? patientDatabase : p);
    }

    public void delete(int idDelete, boolean confirm) {
        patients.removeIf(p -> p.getId() == idDelete && confirm);
    }
}
