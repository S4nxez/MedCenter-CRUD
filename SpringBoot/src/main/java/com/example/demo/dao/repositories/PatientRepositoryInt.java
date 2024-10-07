package com.example.demo.dao.repositories;

import com.example.demo.domain.model.PatientUI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepositoryInt extends JpaRepository<PatientUI, Long> {
    @Query("SELECT p FROM PatientUI p WHERE p.id = ?1")
    Optional<PatientUI> findPatientById(long id);

    @Query("SELECT p FROM PatientUI p WHERE p.phone = :phone")
    Optional<PatientUI> findPatientByPhone(@Param("phone") String phone);
}
