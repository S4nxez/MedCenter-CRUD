package com.example.demo.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Patient {
    private Long        id;
    private String      name;
    private LocalDate   dob;
    private String      phone;
    private Credential credential;
}
