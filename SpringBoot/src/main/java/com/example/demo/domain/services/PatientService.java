package com.example.demo.domain.services;

import com.example.demo.dao.repositories.PatientRepositoryInt;
import com.example.demo.domain.model.PatientUI;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepositoryInt patientRepositoryInt;

    @Autowired
    public PatientService(PatientRepositoryInt patientRepositoryInt) {
        this.patientRepositoryInt = patientRepositoryInt;
    }

    public List<PatientUI> getPatients(){
        return patientRepositoryInt.findAll();
    }

    public PatientUI addNewPatient(PatientUI patientUI) {
        Optional<PatientUI> patientOptional = patientRepositoryInt
                .findPatientByPhone(patientUI.getPhone());
        if (patientOptional.isPresent()){
            throw new IllegalStateException("Phone taken");
        }
        patientRepositoryInt.save(patientUI);
        return patientUI;
    }

    @Transactional
    public PatientUI deletePatient(Long patientId) {
        PatientUI ret = patientRepositoryInt.findById(patientId).orElseThrow(() ->
                new IllegalStateException("Patient with id " + patientId + " does not exist")
        );
        patientRepositoryInt.deleteById(patientId);
        return ret;
    }

    @Transactional
    public PatientUI updatePatient(Long id, String name, String phone) {
        PatientUI patientUI = patientRepositoryInt.findById(id).orElseThrow(()->
                new IllegalStateException("Patient with ID "+id+" does not exists"));
        if(name!=null && !name.isEmpty() && !Objects.equals(patientUI.getName(), name)) patientUI.setName(name);
        if(phone!=null && !phone.isEmpty() && !Objects.equals(patientUI.getPhone(),phone)){
            Optional<PatientUI> patientOptional = patientRepositoryInt.findPatientById(id);
            if(patientOptional.isPresent())
                throw new IllegalStateException("Id taken");
            patientUI.setId(id);
        }
        return patientUI;
    }
}
