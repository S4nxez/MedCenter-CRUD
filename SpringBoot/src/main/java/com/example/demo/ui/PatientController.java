package com.example.demo.ui;

import com.example.demo.domain.model.PatientUI;
import com.example.demo.domain.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="patients")
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
    public PatientUI addNewPatient(@RequestBody PatientUI patientUI){
        return patientServ.addNewPatient(patientUI);
    }

    @DeleteMapping("{idDelete}")
    public ResponseEntity<PatientUI> deletePatient(@PathVariable Long idDelete,
                                                   @RequestParam(required=false) boolean confirm){
        //if (confirm) {
            try {
                PatientUI deletedPatientUI = patientServ.deletePatient(idDelete);
                return ResponseEntity.ok(deletedPatientUI);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
       // } else {
        //   throw new IllegalStateException("Delete not confirmed");
       // }
    }

    @PutMapping
    public PatientUI updatePatient(@RequestBody  PatientUI patientUI){
        return patientServ.updatePatient(patientUI);
    }
}
