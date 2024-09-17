package com.example.demo.medRecord;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedRecordService {

    private final MedRecordRepository repo;

    @Autowired
    public MedRecordService(MedRecordRepository medRepo) {
        this.repo = medRepo;
    }

    public List<MedRecord> getMedRecords(Long patientId) {
        return repo.findAll().stream().filter(medRecord -> Objects.equals(medRecord.getIdPatient(), patientId)).toList();
    }

    @Transactional
    public MedRecord updateMedRecord(Long medRecordId, String description, LocalDate date, Long patientId, Long idDoctor, String medications) {
        Optional<MedRecord> optionalMedRecord = repo.findById(medRecordId);
        if (optionalMedRecord.isPresent()) {
            MedRecord existingMedRecord = optionalMedRecord.get();
            existingMedRecord.setDescription(description);
            existingMedRecord.setDate(date);
            existingMedRecord.setIdPatient(patientId);
            existingMedRecord.setIdDoctor(idDoctor);
            existingMedRecord.setMedications(medications);

            return existingMedRecord;
        } else {
            throw new IllegalArgumentException("MedRecord not found");
        }
    }

    @Transactional
    public MedRecord deleteMedRecord(Long medRecordId) {
        MedRecord ret = repo.getById(medRecordId);
        repo.deleteById(medRecordId);
        return ret;
    }

    public MedRecord addMedRecord(MedRecord medRecord) {
        return repo.save(medRecord);
    }

    public MedRecord updateMedRecord(Long medRecordId, MedRecord medRecord) {
        return Optional.of(medRecord)
                .map(given -> {
                    given.setId(medRecordId);
                    return given;
                })
                .map(repo::save)
                .orElseThrow(() -> new RuntimeException("Error updating mad record"));
    }
}
