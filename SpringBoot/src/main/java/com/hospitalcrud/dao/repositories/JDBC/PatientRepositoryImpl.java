package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.dao.mappers.PatientRowMapperJDBC;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repositories.PatientRepository;
import com.hospitalcrud.utils.Constantes;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("jdbc")
public class PatientRepositoryImpl implements PatientRepository {

    private DBConnection pool;
    private PatientRowMapperJDBC patientRowMapper;

    public PatientRepositoryImpl(DBConnection dbConnection,
                                 PatientRowMapperJDBC patientRowMapper) {
        this.pool = dbConnection;
        this.patientRowMapper = patientRowMapper;
    }

    @Override
    public List<Patient> getAll() {
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(QuerysSQL.SELECT_ALL_PATIENTS);
            return patientRowMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Patient patient) {
        try (Connection connection = pool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement patientStatement = connection.prepareStatement(
                    QuerysSQL.INSERT_INTO_PATIENTS_NAME_DATE_OF_BIRTH_PHONE_VALUES,
                    Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement credentialStatement = connection.prepareStatement(
                         QuerysSQL.INSERT_INTO_CREDENTIALS_USERNAME_PASSWORD_VALUES,
                         Statement.RETURN_GENERATED_KEYS)) {

                if (patient.getCredential() != null) {
                    credentialStatement.setString(1, patient.getCredential().getUsername());
                    credentialStatement.setString(2, patient.getCredential().getPassword());
                    credentialStatement.executeUpdate();
                    try (ResultSet generatedKeys = credentialStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            patient.getCredential().setPatientId(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException(
                                    Constantes.CREATING_CREDENTIAL_FAILED_NO_ID_OBTAINED);
                        }
                    }
                }
                patientStatement.setString(1, patient.getName());
                patientStatement.setDate(2, java.sql.Date.valueOf(patient.getBirthDate()));
                patientStatement.setString(3, patient.getPhone());

                int affectedRows = patientStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException(
                            Constantes.CREATING_PATIENT_FAILED_NO_ROWS_AFFECTED);
                }

                try (ResultSet generatedKeys = patientStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        connection.commit();
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException(
                                QuerysSQL.CREATING_PATIENT_FAILED_NO_ID_OBTAINED);
                    }
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO PONER EL LOGGER COMO EN SU REPO
        }
    }

    @Override
    public void update(Patient patientDatabase) {
        try (Connection con = pool.getConnection();
                 PreparedStatement preparedStatement = con
                         .prepareStatement(QuerysSQL.UPDATE_PATIENT)) {
            preparedStatement.setString(1, patientDatabase.getName());
            preparedStatement.setDate(2,
                    java.sql.Date.valueOf(patientDatabase.getBirthDate()));
            preparedStatement.setString(3, patientDatabase.getPhone());
            preparedStatement.setInt(4, patientDatabase.getId());

            // executeUpdate method for INSERT, UPDATE and DELETE
            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle); //TODO PONER EL LOGGER COMO EN SU REPO
        }
    }

    @Override
    public void delete(int idDelete, boolean confirm) {
        if (!confirm) {
            throw new IllegalArgumentException(Constantes.DELETE_OPERATION_NOT_CONFIRMED);
        }

        try (Connection con = pool.getConnection()) {
            try (PreparedStatement pstmt = con.prepareStatement(
                    QuerysSQL.DELETE_FROM_PATIENTS_WHERE_PATIENT_ID);
                 PreparedStatement pstmt2 = con.prepareStatement(
                         QuerysSQL.DELETE_FROM_USER_LOGIN_WHERE_PATIENT_ID);
            ) {
                con.setAutoCommit(false);

                pstmt.setInt(1, idDelete);
                pstmt2.setInt(1, idDelete);

                pstmt.executeUpdate();
                pstmt2.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw new RuntimeException(e);
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle); //TODO PONER EL LOGGER COMO EN SU REPO
        }
    }
}
