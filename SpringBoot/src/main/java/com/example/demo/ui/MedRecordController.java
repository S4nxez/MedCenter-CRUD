package com.example.demo.ui;

import com.example.demo.domain.model.MedRecordUI;
import com.example.demo.domain.services.MedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="patients/")
public class MedRecordController {
    private final MedRecordService service;

    @Autowired
    public MedRecordController( MedRecordService serv){
        this.service = serv;
    }

    @DeleteMapping("medRecords/{id}")
    public MedRecordUI deleteMedRecord(@PathVariable int id){
        return service.deleteMedRecord(id);
    }

    @GetMapping(path = "{idPatient}/medRecords")
    public List<MedRecordUI> getMedRecords(@PathVariable Long idPatient){ return service.getMedRecords(idPatient);}

    @PutMapping(path = "{medRecordId}")
    public MedRecordUI updateMedRecord(@RequestBody MedRecordUI medRecordUI){
        return service.updateMedRecord(medRecordUI);
    }

    @PostMapping
    public MedRecordUI addMedRecord(@RequestBody MedRecordUI medRecordUI){
        return service.addMedRecord(medRecordUI);
    }
}
