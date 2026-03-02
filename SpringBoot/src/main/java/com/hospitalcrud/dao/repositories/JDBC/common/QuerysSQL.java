package com.hospitalcrud.dao.repositories.JDBC.common;

public class QuerysSQL {
    public static final String INSERT_INTO_CREDENTIALS_USERNAME_PASSWORD_VALUES = "INSERT INTO user_login (username, password, patient_id) VALUES (?, ?, ?)";
    public static final String DELETE_USER_LOGIN = "DELETE FROM user_login WHERE  patient_id = ?";
    public static final String GET_ALL_CREDENTIALS = "SELECT * FROM user_login";
    public static final String GET_CREDENTIAL_BY_USERNAME = "SELECT * FROM user_login WHERE username = ?";

    public static final String INSERT_INTO_PATIENTS_NAME_DATE_OF_BIRTH_PHONE_VALUES = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String DELETE_PATIENT = "DELETE  FROM patients WHERE patient_id = ?";
    public static final String SELECT_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";

    public static final String SELECT_ALL_MEDRECORDS = "SELECT * FROM medical_records WHERE patient_id = ?";
    public static final String INSERT_INTO_MEDICAL_RECORDS = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) VALUES (?,?,?,?)";
    public static final String DELETE_FROM_MED_RECORD = "DELETE FROM medical_records WHERE record_id = ?";
    public static final String SELECT_ALL_MEDRECORDS_BY_PATIENT = "SELECT * FROM medical_records WHERE patient_id = ?";
    public static final String UPDATE_MEDRECORD = "UPDATE medical_records SET patient_id = ?, doctor_id = ?, diagnosis = ?, admission_date = ? WHERE record_id = ?";
    public static final String DELETE_MED_RECORDS_BY_PATIENT = "DELETE FROM medical_records WHERE patient_id = ?";
    public static final String INSERT_MEDICATION_TO_MEDRECORD = "INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?, ?)";

    public static final String DELETE_MEDICATIONS = "DELETE pm from prescribed_medications pm join medical_records md on pm.record_id = md.record_id WHERE patient_id = ?";
    public static final String GET_ALL_MEDICATIONS = "SELECT * FROM prescribed_medications";
    public static final String GET_MEDICATIONS_BY_MEDRECORD = "SELECT *  FROM prescribed_medications WHERE record_id = ?";
    public static final String DELETE_PRESCRIBEDMEDICATION = "DELETE FROM prescribed_medications WHERE record_id = ?";
    public static final String INSERT_INTO_MEDICATIONS = "INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?,?)" ;
    public static final String DELETE_MEDICATION_BY_PRESCRIPTION_ID = "DELETE FROM prescribed_medications WHERE prescription_id= ?";


    public static final String DELETE_APPOINTMENT = "DELETE FROM appointments WHERE patient_id = ?";

    public static final String DELETE_PAYMENT = "DELETE FROM patient_payments WHERE patient_id = ?";
    public static final String GET_ALL_PAYMENTS = "SELECT patient_id, SUM(amount) as amount FROM patient_payments GROUP BY patient_id";

    public static final String GET_ALL_DOCTORS = "SELECT * FROM doctors";

    public QuerysSQL(){
    }
}
