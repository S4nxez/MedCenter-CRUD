package com.hospitalcrud.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private int     userId;
    private String  username;
    private String  password;
    private int     patientId;
    private int     doctorId;
}
