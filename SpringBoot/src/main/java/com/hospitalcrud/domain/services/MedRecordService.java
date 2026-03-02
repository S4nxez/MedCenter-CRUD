package com.hospitalcrud.domain.services;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;

import com.hospitalcrud.dao.repositories.MedRecordRepository;
import com.hospitalcrud.dao.repositories.PatientRepository;
import com.hospitalcrud.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class MedRecordService {


    private final  MedRecordRepository medRecordRepository;

    public MedRecordService(MedRecordRepository medRecordRepository) {this.medRecordRepository = medRecordRepository;}


    public List<MedRecordUI> getAll(int idPatient) {

        List<MedRecordUI> medRecordUIList=new ArrayList<>();

        medRecordRepository.getAll(idPatient).forEach(medRecord -> {
            List<String> listMedications=new ArrayList<>();
            if (medRecord.getMedications() != null)
                medRecord.getMedications().forEach(medication -> listMedications.add(medication.getMedicationName()));
            medRecordUIList.add(new MedRecordUI(medRecord.getId(), medRecord.getDiagnosis(), medRecord.getDate().toString(), medRecord.getIdPatient()
            , medRecord.getIdDoctor(),listMedications));

        });

        return medRecordUIList;

    }

    public int add(MedRecordUI medRecordui) {
        ArrayList<Medication> medications = new ArrayList<>();
        LocalDate date=LocalDate.parse(medRecordui.getDate());

        medRecordui.getMedications().forEach(medication ->
            medications.add(
                    new Medication(
                            medRecordui.getIdPatient(),
                            medRecordui.getMedications().toString(),
                            medRecordui.getId()
                    )
            ));

        MedRecord medRecord= new MedRecord(
                medRecordui.getId(),
                medRecordui.getIdPatient(),
                medRecordui.getIdDoctor(),
                medRecordui.getDescription(),
                date,
                medications
        );

        return medRecordRepository.add(medRecord);
    }

    public void delete(int id) {
        medRecordRepository.delete(id);
    }

    public void update(MedRecordUI medRecordui) {
        LocalDate date = LocalDate.parse(medRecordui.getDate());

        List<Medication> medications = medRecordui.getMedications().stream()
                .filter(Objects::nonNull)
                .map(name -> new Medication(
                        medRecordui.getIdPatient(),
                        name,
                        medRecordui.getId()
                ))
                .collect(Collectors.toList());

        MedRecord medRecord = new MedRecord(
                medRecordui.getId(),
                medRecordui.getIdPatient(),
                medRecordui.getIdDoctor(),
                medRecordui.getDescription(),
                date,
                medications
        );

        medRecordRepository.update(medRecord);
    }
}
