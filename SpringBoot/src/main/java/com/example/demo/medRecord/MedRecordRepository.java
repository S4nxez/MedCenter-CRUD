package com.example.demo.medRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedRecordRepository extends JpaRepository<MedRecord, Long> {
    @Query("SELECT p FROM MedRecord p WHERE p.id = ?1")
    Optional<MedRecord> findById(long id);

}
