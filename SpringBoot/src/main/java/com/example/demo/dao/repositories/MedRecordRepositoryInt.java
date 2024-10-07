package com.example.demo.dao.repositories;

import com.example.demo.domain.model.MedRecordUI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedRecordRepositoryInt extends JpaRepository<MedRecordUI, Long> {
    @Query("SELECT p FROM MedRecordUI p WHERE p.id = ?1")
    Optional<MedRecordUI> findById(long id);
}
