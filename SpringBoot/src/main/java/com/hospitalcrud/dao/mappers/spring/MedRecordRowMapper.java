package com.hospitalcrud.dao.mappers.spring;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.repositories.MedicationRepository;
import com.hospitalcrud.dao.repositories.spring.MedicationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Component
public class MedRecordRowMapper implements RowMapper<MedRecord> {

    private final  MedicationRepository medicationRepository;

    @Override
    public MedRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        MedRecord medRecord = new MedRecord();

        medRecord.setId(rs.getInt("record_id"));
        medRecord.setIdPatient(rs.getInt("patient_id"));
        medRecord.setIdDoctor(rs.getInt("doctor_id"));
        medRecord.setDiagnosis(rs.getString("diagnosis"));
        medRecord.setDate(rs.getDate("admission_date").toLocalDate());

        var medications = medicationRepository.getAll().stream()
                .filter(medication ->
                        medication.getMedRecordId() == medRecord.getId()
                ).toList();

        medRecord.setMedications(medications);

        return medRecord;
    }
}
