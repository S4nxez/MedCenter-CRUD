package com.example.demo.medRecord;

import com.example.demo.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/MedRecord")
public class MedRecordController {
    private final MedRecordService service;

    @Autowired
    public MedRecordController( MedRecordService serv){
        this.service = serv;
    }

    @DeleteMapping(path = "{medRecordId}")
    public MedRecord deleteMedRecord(@PathVariable("medRecordId")Long medRecordId){
        return service.deleteMedRecord(medRecordId);
    }

    @GetMapping(path = "{patientId}")
    public List<MedRecord> getMedRecords(@PathVariable("patientId") Long patientId){ return service.getMedRecords(patientId);}

    @PutMapping(path = "{medRecordId}")
    public MedRecord updateMedRecord(
            @PathVariable("medRecordId") Long medRecordId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate date,
            @RequestParam(required = false) Long idPatient,
            @RequestParam(required = false) Long idDoctor,
            @RequestParam(required = false) String medications){
        return service.updateMedRecord(medRecordId,description,date,idPatient,idDoctor,medications);
    }

    @PostMapping
    public MedRecord addMedRecord(@RequestBody MedRecord medRecord){
        return service.addMedRecord(medRecord);
    }
}
