package com.hospitalcrud.ui;


import com.hospitalcrud.domain.model.DoctorUI;
import com.hospitalcrud.domain.services.DoctorService;
import com.hospitalcrud.utils.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestDoctor {

    private final DoctorService doctorService;

    public RestDoctor(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping(Constants.PATH_DOCTOR)
    public List<DoctorUI> index() {
        return doctorService.getAll();
    }

}
