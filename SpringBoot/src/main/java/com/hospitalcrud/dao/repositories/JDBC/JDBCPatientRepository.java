package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.dao.mappers.PatientRowMapperJDBC;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repositories.PatientRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("jdbc")
public class JDBCPatientRepository implements PatientRepository {

    private DBConnection dbConnection;
    private PatientRowMapperJDBC patientRowMapper;
    private JDBCCredential jdbcCredential;

    public JDBCPatientRepository(DBConnection dbConnection, PatientRowMapperJDBC patientRowMapper, JDBCCredential jdbcCredential) {
        this.dbConnection = dbConnection;
        this.patientRowMapper = patientRowMapper;
        this.jdbcCredential = jdbcCredential;
    }

    @Override
    public List<Patient> getAll() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from patients");
            return patientRowMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Patient patient) {

        String patientSql = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
        String credentialSql = "INSERT INTO credentials (username, password) VALUES (?, ?)";

        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement patientStatement = connection.prepareStatement(patientSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement credentialStatement = connection.prepareStatement(credentialSql, Statement.RETURN_GENERATED_KEYS)) {

                if (patient.getCredential() != null) {
                    credentialStatement.setString(1, patient.getCredential().getUsername());
                    credentialStatement.setString(2, patient.getCredential().getPassword());
                    credentialStatement.executeUpdate();

                    try (ResultSet generatedKeys = credentialStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            patient.getCredential().setPatientId(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("Creating credential failed, no ID obtained.");
                        }
                    }
                }

                patientStatement.setString(1, patient.getName());
                patientStatement.setDate(2, java.sql.Date.valueOf(patient.getBirthDate()));
                patientStatement.setString(3, patient.getPhone());

                int affectedRows = patientStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating patient failed, no rows affected.");
                }

                try (ResultSet generatedKeys = patientStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        connection.commit();
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating patient failed, no ID obtained.");
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

    public int add(Credential credential) {//poner también el id del paciente, meter este metodo con las transacciones directamente en el otro no llamar de repo a repo
        String sql = "INSERT INTO credentials (username, password) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, credential.getUsername());
            preparedStatement.setString(2, credential.getPassword());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating credential failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating credential failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO PONER EL LOGGER COMO EN SU REPO
        }
    }

    @Override
    public void update(Patient patientDatabase) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("update coffees set name = ?, date_of_birth = ?, phone = ? where patientId = ?")) {
            preparedStatement.setString(1, patientDatabase.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(patientDatabase.getBirthDate()));
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
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("DELETE FROM patients WHERE patientId = ?")) {
            preparedStatement.setInt(1, idDelete); //borrar el login con una transacción
            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle); //TODO PONER EL LOGGER COMO EN SU REPO
        }
    }
}
