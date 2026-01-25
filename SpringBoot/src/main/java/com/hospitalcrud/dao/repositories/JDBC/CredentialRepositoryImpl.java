package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.dao.mappers.JDBC.CredentialRowMapperJDBC;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repositories.CredentialRepository;
import com.hospitalcrud.dao.repositories.JDBC.common.PoolDBConnection;
import com.hospitalcrud.dao.repositories.JDBC.common.QuerysSQL;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class CredentialRepositoryImpl implements CredentialRepository {
    private PoolDBConnection pool;
    private CredentialRowMapperJDBC rowMapper;

    public CredentialRepositoryImpl(PoolDBConnection pool, CredentialRowMapperJDBC doctorRowMapper) {
        this.pool = pool;
        this.rowMapper = doctorRowMapper;
    }

    @Override
    public List<Credential> getAll() {
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(QuerysSQL.GET_ALL_CREDENTIALS);

            return rowMapper.mapRow(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Credential> findByUsername(String username) {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     QuerysSQL.GET_CREDENTIAL_BY_USERNAME)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            List<Credential> result = rowMapper.mapRow(rs);

            return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
