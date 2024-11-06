package com.hospitalcrud.domain.services;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;


import com.hospitalcrud.dao.repositories.PatientRepository;
import com.hospitalcrud.domain.model.PatientUI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientUI> get(){
        List<Patient> patient = patientRepository.getAll();
        List<PatientUI> patientsUI = new ArrayList<>();

        patient.forEach(patient1 -> patientsUI.add(new PatientUI(patient1.getId(),patient1.getName(),
                patient1.getBirthDate(),patient1.getPhone(),0,null,null)));
        return patientsUI;
    }

   public int add(PatientUI patientui) {
        Patient patient=new Patient(patientui.getBirthDate(),
                new Credential(patientui.getUserName(), patientui.getPassword(), patientui.getId()),
                patientui.getId(),patientui.getName(),patientui.getPhone());

        return patientRepository.add(patient);
    }

    public void update(PatientUI patientui) {
        Patient patient = new Patient(patientui.getBirthDate(),
                new Credential(patientui.getUserName(), patientui.getPassword(), patientui.getId()),
                patientui.getId(), patientui.getName(), patientui.getPhone());

        patientRepository.update(patient);
    }

    public void delete(int idDelete, boolean confirm) {
        patientRepository.delete(idDelete, confirm);
    }
}
