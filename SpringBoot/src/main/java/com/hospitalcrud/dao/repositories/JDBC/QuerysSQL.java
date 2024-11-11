package com.hospitalcrud.dao.repositories.JDBC;

public class QuerysSQL {
    public static final String INSERT_INTO_CREDENTIALS_USERNAME_PASSWORD_VALUES = "INSERT INTO credentials (username, password) VALUES (?, ?)";
    public static final String INSERT_INTO_PATIENTS_NAME_DATE_OF_BIRTH_PHONE_VALUES = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String DELETE_FROM_PATIENTS_WHERE_PATIENT_ID = "DELETE  FROM patients WHERE patientId = ?";
    public static final String DELETE_FROM_USER_LOGIN_WHERE_PATIENT_ID = "DELETE FROM user_login WHERE  patientId = ?";
    public static final String CREATING_PATIENT_FAILED_NO_ID_OBTAINED = "Creating patient failed, no ID obtained.";
    public static final String SELECT_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? where patientId = ?";
    public static final String SELECT_ALL_MEDRECORDS = "SELECT * FROM medical_records";
    public static final String INSERT_INTO_MEDICAL_RECORDS = "INSERT INTO medical_records (record_id, patient_id, doctor_id, diagnosis, admission_date) VALUES (?,?,?,?,?)";

    public QuerysSQL(){
    }
}
