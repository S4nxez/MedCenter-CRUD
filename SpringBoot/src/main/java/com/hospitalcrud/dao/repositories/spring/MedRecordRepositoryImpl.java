package com.hospitalcrud.dao.repositories.spring;

import com.hospitalcrud.dao.mappers.spring.MedRecordRowMapper;
import com.hospitalcrud.dao.mappers.spring.MedicationRowMapper;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repositories.JDBC.common.QuerysSQL;
import com.hospitalcrud.dao.repositories.MedRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("spring")
@RequiredArgsConstructor
public class MedRecordRepositoryImpl implements MedRecordRepository {

    private final JdbcClient jdbcClient;

    private final MedRecordRowMapper medRecordRowMapperSpring;

    private final MedicationRepositoryImpl medicationRepository;

    private final MedicationRowMapper medicationRowMapper;

    @Override
    public List<MedRecord> getAll(int idPatient) {
        return jdbcClient.sql(QuerysSQL.SELECT_ALL_MEDRECORDS_BY_PATIENT)
                .param(1,idPatient)
                .query(medRecordRowMapperSpring)
                .list();
    }

    @Override
    @Transactional
    public int add(MedRecord medRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(QuerysSQL.INSERT_INTO_MEDICAL_RECORDS)
                .param(1,medRecord.getIdPatient())
                .param(2,medRecord.getIdDoctor())
                .param(3,medRecord.getDiagnosis())
                .param(4,medRecord.getDate())
                .update(keyHolder);
        int newId = Objects.requireNonNull(keyHolder.getKey(), "Key not generated").intValue();
        medRecord.setId(newId);
        medRecord.getMedications().forEach(medication -> medication.setMedRecordId(newId));
        medRecord.getMedications().forEach(medicationRepository::add);
        return newId;
    }

    @Override
    @Transactional
    public void update(MedRecord medRecord) {
        List<Medication> currentMedications = jdbcClient.sql(QuerysSQL.GET_MEDICATIONS_BY_MEDRECORD)
                .param(1, medRecord.getId())
                .query(medicationRowMapper)
                .list();

        Set<String> currentNames = currentMedications.stream()
                .map(Medication::getMedicationName)
                .collect(Collectors.toSet());

        Set<String> newNames = medRecord.getMedications().stream()
                .map(Medication::getMedicationName)
                .collect(Collectors.toSet());

        Set<String> toDeleteNames = new HashSet<>(currentNames);
        toDeleteNames.removeAll(newNames);

        Set<String> toAddNames = new HashSet<>(newNames);
        toAddNames.removeAll(currentNames);

        for (Medication m : currentMedications) {
            if (toDeleteNames.contains(m.getMedicationName())) {
                jdbcClient.sql(QuerysSQL.DELETE_MEDICATION_BY_PRESCRIPTION_ID)
                        .param(1, m.getId())
                        .update();
            }
        }

        for (String name : toAddNames) {
            jdbcClient.sql(QuerysSQL.INSERT_MEDICATION_TO_MEDRECORD)
                    .param(1, medRecord.getId())
                    .param(2, name)
                    .update();
        }

        jdbcClient.sql(QuerysSQL.UPDATE_MEDRECORD)
                .param(1, medRecord.getIdPatient())
                .param(2, medRecord.getIdDoctor())
                .param(3, medRecord.getDiagnosis())
                .param(4, medRecord.getDate())
                .param(5, medRecord.getId())
                .update();
    }

    @Override
    @Transactional
    public void delete(int medRecordId) {
        if (medRecordId != 0){
            medicationRepository.delete(medRecordId);
            jdbcClient.sql(QuerysSQL.DELETE_FROM_MED_RECORD)
                    .param(1,medRecordId)
                    .update();
        }else{
            medicationRepository.delete(medRecordId);
            jdbcClient.sql(QuerysSQL.DELETE_MED_RECORDS_BY_PATIENT)
                    .param(1,medRecordId)
                    .update();
        }
    }
}
