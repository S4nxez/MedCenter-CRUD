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
    public MedRecordUI updateMedRecord(MedRecordUI updatedMedRecordUI) {
        Optional<MedRecordUI> optionalMedRecord = repo.findById(updatedMedRecordUI.getId());
        if (optionalMedRecord.isPresent()) {
            MedRecordUI existingMedRecordUI = optionalMedRecord.get();
            existingMedRecordUI.setDescription(updatedMedRecordUI.getDescription());
            existingMedRecordUI.setDate(updatedMedRecordUI.getDate());
            existingMedRecordUI.setIdPatient(updatedMedRecordUI.getIdPatient());
            existingMedRecordUI.setIdDoctor(updatedMedRecordUI.getIdDoctor());
            existingMedRecordUI.setMedications(updatedMedRecordUI.getMedications());

            return existingMedRecordUI;
        } else {
            throw new IllegalArgumentException("MedRecord not found");
        }
    }

    @Transactional
    public MedRecordUI deleteMedRecord(int medRecordId) {
        MedRecordUI ret = repo.getById((long)medRecordId);
        repo.deleteById((long)medRecordId);
        return ret;
    }

    public MedRecordUI addMedRecord(MedRecordUI medRecordUI) {
        return repo.save(medRecordUI);
    }
}
