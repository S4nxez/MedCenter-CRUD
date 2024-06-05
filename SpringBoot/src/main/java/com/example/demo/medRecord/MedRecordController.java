package com.example.demo.medRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/MedRecord")
public class MedRecordController {
    private final MedRecordService service;

    @Autowired
    public MedRecordController( MedRecordService serv){
        this.service = serv;
    }

    @GetMapping
    public List<MedRecord> getMedRecords(){ return service.getMedRecords();}
}
