package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repositories.CredentialRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Profile("jdbc")
public class JDBCCredential implements CredentialRepository {

    private DBConnection dbConnection;

    public JDBCCredential(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Credential get(String username) {
        return null;
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

}
