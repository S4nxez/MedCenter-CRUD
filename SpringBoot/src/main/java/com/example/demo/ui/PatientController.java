package com.example.demo.ui;

import com.example.demo.domain.model.PatientUI;
import com.example.demo.domain.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Patient")
public class PatientController {
    private final PatientService patientServ;

    @Autowired
    public PatientController(PatientService patientServ) {
        this.patientServ = patientServ;
    }

    @GetMapping
    public List<PatientUI> getPatients(){
        return patientServ.getPatients();
    }

    @PostMapping
    public PatientUI registerNewPatient(@RequestBody PatientUI patientUI){
        return patientServ.addNewPatient(patientUI);
    }

    @DeleteMapping(path = "{patientId}")
    public ResponseEntity<PatientUI> deletePatient(@PathVariable("patientId") Long patientId,
                                                   @RequestParam("confirm") boolean confirm){
        if (confirm) {
            try {
                PatientUI deletedPatientUI = patientServ.deletePatient(patientId);
                return ResponseEntity.ok(deletedPatientUI);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            throw new IllegalStateException("Delete not confirmed");
        }
    }

    @PutMapping(path = "{patientId}")
    public PatientUI updatePatient(
            @PathVariable("patientId") Long patientId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String Phone){
        return patientServ.updatePatient(patientId,name,Phone);
    }
}
