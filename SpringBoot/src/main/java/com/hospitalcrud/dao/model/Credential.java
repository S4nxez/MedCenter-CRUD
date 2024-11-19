package com.hospitalcrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private String username;
    private String password;
    private int patientId;
}
