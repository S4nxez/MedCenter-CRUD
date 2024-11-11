package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.dao.mappers.MedRecordRowMapper;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.repositories.MedRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("jdbc")
public class MedRecordRepositoryImpl implements MedRecordRepository {

    private DBConnection pool;
    private MedRecordRowMapper rowMapper;

    public MedRecordRepositoryImpl(DBConnection pool, MedRecordRowMapper rowMapper) {
        this.pool = pool;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<MedRecord> getAll(int idPatient) {
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(QuerysSQL.SELECT_ALL_MEDRECORDS);
            return rowMapper.mapRow(rs);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(MedRecord medRecord) {
        try (Connection connection = pool.getConnection()){
            connection.setAutoCommit(false);
            try (PreparedStatement medrecordStatement = connection.prepareStatement(
                    QuerysSQL.INSERT_INTO_MEDICAL_RECORDS,
                    Statement.RETURN_GENERATED_KEYS)){

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0; //TODO
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(MedRecord medRecord) {

    }
}
