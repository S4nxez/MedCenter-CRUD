package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.dao.mappers.MedicationRowMapper;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repositories.JDBC.common.PoolDBConnection;
import com.hospitalcrud.dao.repositories.JDBC.common.QuerysSQL;
import com.hospitalcrud.dao.repositories.MedicationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("jdbc")
public class MedicationRepositoryImpl implements MedicationRepository {
    private final PoolDBConnection pool;
    private final MedicationRowMapper rowMapper;

    public MedicationRepositoryImpl(PoolDBConnection pool, MedicationRowMapper rowMapper) {
        this.pool = pool;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Medication> getAll() {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(QuerysSQL.GET_ALL_MEDICATIONS)) {

            ResultSet rs = preparedStatement.executeQuery();
            return rowMapper.mapRow(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error getting medications", e);
        }
    }
}
