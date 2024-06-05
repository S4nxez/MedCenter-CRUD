package com.example.demo.medRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedRecordRepository extends JpaRepository<MedRecord, Long> {
}
