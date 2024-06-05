package com.example.demo.medRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedRecordService {

    private final MedRecordRepository repo;

    @Autowired
    public MedRecordService(MedRecordRepository medRepo) {
        this.repo = medRepo;
    }

    public List<MedRecord> getMedRecords() {
        return repo.findAll();
    }
}
