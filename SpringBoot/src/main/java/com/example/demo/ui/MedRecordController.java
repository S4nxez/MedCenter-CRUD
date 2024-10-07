package com.example.demo.ui;

import com.example.demo.domain.model.MedRecordUI;
import com.example.demo.domain.services.MedRecordService;
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
    public MedRecordUI deleteMedRecord(@PathVariable("medRecordId")Long medRecordId){
        return service.deleteMedRecord(medRecordId);
    }

    @GetMapping(path = "{patientId}")
    public List<MedRecordUI> getMedRecords(@PathVariable("patientId") Long patientId){ return service.getMedRecords(patientId);}

    @PutMapping(path = "{medRecordId}")
    public MedRecordUI updateMedRecord(@RequestBody MedRecordUI medRecordUI){
        return service.updateMedRecord(medRecordUI);
    }

    @PostMapping
    public MedRecordUI addMedRecord(@RequestBody MedRecordUI medRecordUI){
        return service.addMedRecord(medRecordUI);
    }
}
