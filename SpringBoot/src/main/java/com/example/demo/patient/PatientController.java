package com.example.demo.patient;

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
    public List<Patient> getPatients(){
        return patientServ.getPatients();
    }

    @PostMapping
    public Patient registerNewPatient(@RequestBody Patient patient){
        return patientServ.addNewPatient(patient);
    }

    @DeleteMapping(path = "{patientId}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("patientId") Long patientId,
                                                 @RequestParam("confirm") boolean confirm){
        if (confirm) {
            try {
                Patient deletedPatient = patientServ.deletePatient(patientId);
                return ResponseEntity.ok(deletedPatient);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            throw new IllegalStateException("Delete not confirmed");
        }
    }

    @PutMapping(path = "{patientId}")
    public Patient updatePatient(
            @PathVariable("patientId") Long patientId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String Phone){
        return patientServ.updatePatient(patientId,name,Phone);
    }
}
