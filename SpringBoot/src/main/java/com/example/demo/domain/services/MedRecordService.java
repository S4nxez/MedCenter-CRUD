package com.example.demo.domain.services;

import com.example.demo.dao.repositories.MedRecordRepositoryInt;
import com.example.demo.domain.model.MedRecordUI;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedRecordService {

    private final MedRecordRepositoryInt repo;

    @Autowired
    public MedRecordService(MedRecordRepositoryInt medRepo) {
        this.repo = medRepo;
    }

    public List<MedRecordUI> getMedRecords(Long patientId) {
        return repo.findAll().stream().filter(medRecordUI -> Objects.equals(medRecordUI.getIdPatient(), patientId)).toList();
    }

    @Transactional
    public MedRecordUI updateMedRecord(Long medRecordId, String description, LocalDate date, Long patientId, Long idDoctor, String medications) {
        Optional<MedRecordUI> optionalMedRecord = repo.findById(medRecordId);
        if (optionalMedRecord.isPresent()) {
            MedRecordUI existingMedRecordUI = optionalMedRecord.get();
            existingMedRecordUI.setDescription(description);
            existingMedRecordUI.setDate(date);
            existingMedRecordUI.setIdPatient(patientId);
            existingMedRecordUI.setIdDoctor(idDoctor);
            existingMedRecordUI.setMedications(medications);

            return existingMedRecordUI;
        } else {
            throw new IllegalArgumentException("MedRecord not found");
        }
    }

    @Transactional
    public MedRecordUI deleteMedRecord(Long medRecordId) {
        MedRecordUI ret = repo.getById(medRecordId);
        repo.deleteById(medRecordId);
        return ret;
    }

    public MedRecordUI addMedRecord(MedRecordUI medRecordUI) {
        return repo.save(medRecordUI);
    }
}
