package com.hospitalcrud.ui;

import com.hospitalcrud.domain.model.PatientUI;
import com.hospitalcrud.domain.services.PatientService;
import com.hospitalcrud.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestPatient {
    private final PatientService patientService;

    public RestPatient(PatientService patientService){
        this.patientService=patientService;
    }

    @GetMapping("/patients")
    public List<PatientUI> get(){
        return patientService.get();
    }

    @PostMapping("/patients")
    public int add(@RequestBody  PatientUI patientUI){
        return patientService.add(patientUI);
    }

    @DeleteMapping("/patients/{idDelete}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int idDelete, @RequestParam(required=false) boolean confirm){
        patientService.delete(idDelete, true);
    }

    @PutMapping(Constants.PATIENTS_PATH)
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody  PatientUI patientUI){
        patientService.update(patientUI);
    }
}
