package com.hospitalcrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Payment {

    private int payment_id;
    private int patient_id;
    private float amount;
    private LocalDate date;

}
