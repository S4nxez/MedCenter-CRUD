package com.example.demo.patient;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    public Patient addNewPatient(Patient patient) {
        Optional<Patient> patientOptional = patientRepository
                .findPatientByPhone(patient.getPhone());
        if (patientOptional.isPresent()){
            throw new IllegalStateException("Phone taken");
        }
        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public Patient deletePatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new IllegalStateException("Patient with id " + patientId + " does not exist");
        }
        Patient ret = patientRepository.findById(patientId).orElseThrow(() ->
                new IllegalStateException("Patient with id " + patientId + " does not exist")
        );
        patientRepository.deleteById(patientId);
        return ret;
    }

    @Transactional
    public Patient updatePatient(Long id, String name, String phone) {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new IllegalStateException("Patient with ID "+id+" does not exists"));
        if(name!=null && !name.isEmpty() && !Objects.equals(patient.getName(), name)) patient.setName(name);
        if(phone!=null && !phone.isEmpty() && !Objects.equals(patient.getPhone(),phone)){
            Optional<Patient> patientOptional = patientRepository.findPatientById(id);
            if(patientOptional.isPresent())
                throw new IllegalStateException("Email taken");
            patient.setId(id);
        }
        return patient;
    }
}
